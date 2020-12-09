package com.vd.movies.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vd.movies.data.db.entity.Movie
import com.vd.movies.data.repository.IRepository
import com.vd.movies.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel() : BaseViewModel("Details") {
    val isLoaderVisible = MutableLiveData(true)
    val isContentVisible = MutableLiveData(false)
    val isNoInternetLblVisible = MutableLiveData(false)
    val movie = MutableLiveData<Movie?>(null)

    fun init(repository: IRepository, imdbId: String) {
        this.repository = repository
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getMovieByImdbId(imdbId)
            movie.postValue(result)
            isLoaderVisible.postValue(false)
            isContentVisible.postValue(result?.details != null)
            isNoInternetLblVisible.postValue(result?.details == null)
            title.postValue(result?.title ?: "Movie" + " Details")
        }
    }

    fun onAddToWatchListPressed() {
        movie.value?.let {
            it.isAddedToWatchList = !it.isAddedToWatchList
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateMovie(it)
                movie.postValue(it)
            }
        }
    }

    fun onAddToWatchedListPressed() {
        movie.value?.let {
            it.isAddedToWatchedList = !it.isAddedToWatchedList
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateMovie(it)
                movie.postValue(it)
            }
        }
    }

    fun onAddToFavoritesPressed() {
        movie.value?.let {
            it.isAddedToFavorites = !it.isAddedToFavorites
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateMovie(it)
                movie.postValue(it)
            }
        }
    }
}