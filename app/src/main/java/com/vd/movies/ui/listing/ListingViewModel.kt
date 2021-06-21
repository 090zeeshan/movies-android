package com.vd.movies.ui.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import com.vd.movies.data.db.entity.Movie
import com.vd.movies.data.repository.Repository
import com.vd.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(repository: Repository, handle: SavedStateHandle) : BaseViewModel(repository) {
    lateinit var movies: LiveData<List<Movie>>
    val isLoaderVisible = MutableLiveData(true)
    val isNotDataLabelVisible = MutableLiveData(false)
    var isListVisible: LiveData<Boolean> = MutableLiveData(false)
    private val listingType = handle["listingType"]?: ListingType.FAVORITES

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

    override fun onCleared() {
        super.onCleared()
        Timber.i(  "onCleared")
    }

}