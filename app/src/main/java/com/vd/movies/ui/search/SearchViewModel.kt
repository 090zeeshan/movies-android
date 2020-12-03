package com.vd.movies.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vd.movies.data.repository.IRepository
import com.vd.movies.data.model.Movie
import com.vd.movies.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel("Search") {
    val searchKey = MutableLiveData<String>("")
    val moviesList = MutableLiveData<List<Movie>>(emptyList())
    val isLoaderVisible = MutableLiveData(false)
    val isNoDataLabelVisible = MutableLiveData(false)
    val isListVisible = MutableLiveData(false)

    fun init(repository: IRepository, searchKey: String) {
        this.searchKey.value = searchKey
        this.repository = repository
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
}