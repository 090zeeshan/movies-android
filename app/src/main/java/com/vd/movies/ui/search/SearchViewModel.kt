package com.vd.movies.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vd.movies.data.db.entity.Movie
import com.vd.movies.data.repository.Repository
import com.vd.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(repository: Repository, handle: SavedStateHandle) :
    BaseViewModel(repository, "Search") {
    val searchKey = MutableLiveData<String>(handle["searchKey"])
    val moviesList = MutableLiveData<List<Movie>>(emptyList())
    val isLoaderVisible = MutableLiveData(false)
    val isNoDataLabelVisible = MutableLiveData(false)
    val isListVisible = MutableLiveData(false)

    init {
        search(searchKey.value ?: "")
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