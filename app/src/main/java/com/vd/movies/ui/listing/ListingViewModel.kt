package com.vd.movies.ui.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.vd.movies.data.db.entity.Movie
import com.vd.movies.data.repository.IRepository
import com.vd.movies.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListingViewModel : BaseViewModel() {
    lateinit var movies: LiveData<List<Movie>>
    val isLoaderVisible = MutableLiveData(true)
    val isNotDataLabelVisible = MutableLiveData(false)
    var isListVisible: LiveData<Boolean> = MutableLiveData(false)

    fun init(repository: IRepository, listingType: ListingType) {
        this.repository = repository

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
}