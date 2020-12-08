package com.vd.movies.ui.listing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vd.movies.data.repository.IRepository
import com.vd.movies.data.model.Movie
import com.vd.movies.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListingViewModel : BaseViewModel() {
    val movies = MutableLiveData(emptyList<Movie>())
    val isLoaderVisible = MutableLiveData(true)
    val isNotDataLabelVisible = MutableLiveData(false)
    val isListVisible = MutableLiveData(false)

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
        viewModelScope.launch(Dispatchers.IO) {
            val movieList = when (listingType) {
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
            movies.postValue(movieList)
            isLoaderVisible.postValue(false)
            isNotDataLabelVisible.postValue(movieList.isEmpty())
            isListVisible.postValue(movieList.isNotEmpty())
        }
    }

    fun onItemClicked(movie: Movie) {
        navigate(ListingFragmentDirections.actionDetailsFragment(movie.imdbId))
    }
}