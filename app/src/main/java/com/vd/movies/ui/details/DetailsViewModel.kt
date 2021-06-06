package com.vd.movies.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.vd.movies.data.db.entity.Movie
import com.vd.movies.data.repository.Repository
import com.vd.movies.ui.base.BaseViewModel
import com.vd.movies.ui.webview.WebViewFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DetailsViewModel(repository: Repository, val imdbId: String) :
    BaseViewModel(repository, "Details") {
    val isLoaderVisible = MutableLiveData(true)
    val isContentVisible = MutableLiveData(false)
    val isNoInternetLblVisible = MutableLiveData(false)
    val movie = MutableLiveData<Movie?>(null)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("DetailsViewModel", imdbId);
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

    fun onViewOnImdbPressed() {
        val url = "https://www.imdb.com/title/${imdbId}"
        Log.i("URL", url);
        navigate(DetailsFragmentDirections.actionOpenImdb(url))
    }

    class Factory(val repository: Repository, val imdbId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Repository::class.java, String::class.java)
                .newInstance(repository, imdbId)
        }
    }

}