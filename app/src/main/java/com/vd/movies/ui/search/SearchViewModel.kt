package com.vd.movies.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vd.movies.data.db.entity.Movie
import com.vd.movies.data.repository.Repository
import com.vd.movies.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(repository: Repository, searchKey: String) : BaseViewModel(repository,"Search") {
    val searchKey = MutableLiveData<String>(searchKey)
    val moviesList = MutableLiveData<List<Movie>>(emptyList())
    val isLoaderVisible = MutableLiveData(false)
    val isNoDataLabelVisible = MutableLiveData(false)
    val isListVisible = MutableLiveData(false)

    init {
        search(searchKey)
    }

    fun search(key: String) {
        if (key.isEmpty()) {
            return
        }

        isLoaderVisible.value = true
        isNoDataLabelVisible.value = false
        isListVisible.value = false

        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.searchMovies(key)
            moviesList.postValue(result)
            isLoaderVisible.postValue(false)
            isNoDataLabelVisible.postValue(result.isEmpty())
            isListVisible.postValue(result.isNotEmpty())
            title.postValue("Search results for \"${searchKey.value}\"")
            searchKey.postValue("")
        }
    }

    fun onSearchClicked() {
        search(searchKey.value ?: "")
    }

    fun onItemClicked(movie: Movie) {
        navigate(SearchFragmentDirections.actionDetailsFragment(movie.imdbId))
    }

    class Factory(val repository: Repository, val searchKey: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Repository::class.java, String::class.java).newInstance(repository, searchKey)
        }
    }
}