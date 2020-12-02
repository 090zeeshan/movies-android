package com.vd.movies.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vd.movies.repository.IRepository
import com.vd.movies.repository.Repository
import com.vd.movies.repository.model.Movie
import com.vd.movies.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : BaseViewModel("Details") {
    val repository: IRepository = Repository()
    val isLoaderVisible = MutableLiveData(true)
    val movie = MutableLiveData<Movie>(null)

    fun init(imdbId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getMovieByImdbId(imdbId)
            movie.postValue(result)
            isLoaderVisible.postValue(false)
            title.postValue(result.title + " Details")
        }
    }
}