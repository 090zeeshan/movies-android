package com.vd.movies.ui.search

import androidx.lifecycle.*
import com.vd.movies.repository.IRepository
import com.vd.movies.repository.Repository
import com.vd.movies.repository.model.Movie
import kotlinx.coroutines.*

class SearchViewModel : ViewModel() {
    val repository: IRepository by lazy { Repository() }
    val searchKey = MutableLiveData<String>("")
    val moviesList = MutableLiveData<List<Movie>>(emptyList())
    val isLoaderVisible = MutableLiveData(false)
    val isNoDataLabelVisible = MutableLiveData(false)
    val isListVisible = MutableLiveData(false)

    fun init(searchKey: String) {
        this.searchKey.value = searchKey
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
            searchKey.postValue("")
        }
    }

    fun onSearchPressed() {
        search(searchKey.value ?: "")
    }
}