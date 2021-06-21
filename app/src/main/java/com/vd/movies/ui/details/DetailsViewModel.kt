package com.vd.movies.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vd.movies.data.db.entity.Movie
import com.vd.movies.data.repository.Repository
import com.vd.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(repository: Repository, handle: SavedStateHandle) :
    BaseViewModel(repository, "Details") {
    val isLoaderVisible = MutableLiveData(true)
    val isContentVisible = MutableLiveData(false)
    val isNoInternetLblVisible = MutableLiveData(false)
    val movie = MutableLiveData<Movie?>(null)
    val imdbId = handle.get("imdbId") ?: ""

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Timber.i(imdbId)
            val result = repository.fetchMovieByImdbId(imdbId)
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

    fun onViewOnImdbPressed() {
        val url = "https://www.imdb.com/title/${imdbId}"
        Timber.i(url)
        navigate(DetailsFragmentDirections.actionOpenImdb(url))
    }

}