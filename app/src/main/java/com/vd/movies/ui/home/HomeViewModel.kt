package com.vd.movies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.vd.movies.data.db.entity.Movie
import com.vd.movies.data.repository.Repository
import com.vd.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: Repository) : BaseViewModel(repository,"Movies") {
    private val recentCount = 5
    val searchKey = MutableLiveData("")

    var recentFavorites: LiveData<List<Movie>>
    var recentWatched: LiveData<List<Movie>>
    var recentWatchlist: LiveData<List<Movie>>

    var isFavsDataAvailable: LiveData<Boolean>
    var isWatchlistDataAvailable: LiveData<Boolean>
    var isWatchedListDataAvailable: LiveData<Boolean>

    init {
        recentFavorites = repository.fetchFavoriteMovies(recentCount)
        isFavsDataAvailable = Transformations.map(recentFavorites) { it.isNotEmpty() }

        recentWatchlist = repository.fetchWatchlistMovies(recentCount)
        isWatchlistDataAvailable = Transformations.map(recentWatchlist) { it.isNotEmpty() }

        recentWatched = repository.fetchWatchedListMovies(recentCount)
        isWatchedListDataAvailable = Transformations.map(recentWatched) { it.isNotEmpty() }
    }

    fun onSearchClicked() {
        navigate(HomeFragmentDirections.actionSearchFragment(searchKey.value.toString()))
        searchKey.value = ""
    }

    fun onItemClicked(movie: Movie) {
        navigate(HomeFragmentDirections.actionDetailsFragment(movie.imdbId))
    }

    fun onViewAllFavoriteClicked() {
        navigate(HomeFragmentDirections.actionFavoritesFragment())
    }

    fun onViewAllWatchlistClicked() {
        navigate(HomeFragmentDirections.actionWatchListFragment())
    }

    fun onViewAllWatchedListClicked() {
        navigate(HomeFragmentDirections.actionWatchedFragment())
    }

}