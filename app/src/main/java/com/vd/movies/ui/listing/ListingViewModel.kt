package com.vd.movies.ui.listing

import androidx.lifecycle.*
import com.vd.movies.data.db.entity.Movie
import com.vd.movies.data.repository.Repository
import com.vd.movies.ui.base.BaseViewModel

class ListingViewModel(repository: Repository, listingType: ListingType) : BaseViewModel(repository) {
    lateinit var movies: LiveData<List<Movie>>
    val isLoaderVisible = MutableLiveData(true)
    val isNotDataLabelVisible = MutableLiveData(false)
    var isListVisible: LiveData<Boolean> = MutableLiveData(false)

    init {
        title.value = when (listingType) {
            ListingType.FAVORITES -> "Favorites"
            ListingType.WATCHED -> "Watched List"
            ListingType.WATCHLIST -> "Watchlist"
        }
        fetchList(listingType)
    }

    private fun fetchList(listingType: ListingType) {
        movies = when (listingType) {
            ListingType.FAVORITES -> {
                repository.fetchFavoriteMovies()
            }
            ListingType.WATCHED -> {
                repository.fetchWatchedListMovies()
            }
            ListingType.WATCHLIST -> {
                repository.fetchWatchlistMovies()
            }
        }

        isListVisible  = Transformations.switchMap(movies) {
            isLoaderVisible.value = false
            isNotDataLabelVisible.value = it.isEmpty()
            MutableLiveData<Boolean>( it.isNotEmpty())
        }
    }

    fun onItemClicked(movie: Movie) {
        navigate(ListingFragmentDirections.actionDetailsFragment(movie.imdbId))
    }

    class Factory(val repository: Repository, val listingType: ListingType) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Repository::class.java, ListingType::class.java).newInstance(repository, listingType)
        }
    }
}