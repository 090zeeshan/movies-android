package com.vd.movies.ui.home

import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.vd.movies.data.db.entity.Movie
import com.vd.movies.data.repository.IRepository
import com.vd.movies.data.repository.Repository
import com.vd.movies.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel("Movies") {
    private val recentCount = 5;
    val searchKey = MutableLiveData("")

    lateinit var recentFavorites :LiveData<List<Movie>>
    lateinit var recentWatched : LiveData<List<Movie>>
    lateinit var recentWatchlist : LiveData<List<Movie>>

    lateinit var isFavsDataAvailable  : LiveData<Boolean>
    lateinit var isWatchlistDataAvailable : LiveData<Boolean>
    lateinit var isWatchedListDataAvailable : LiveData<Boolean>

    fun init(repository: IRepository){
        this.repository = repository

        recentFavorites = repository.fetchFavoriteMovies(recentCount)
        isFavsDataAvailable = Transformations.map(recentFavorites) { it.isNotEmpty() }

        recentWatchlist = repository.fetchWatchlistMovies(recentCount)
        isWatchlistDataAvailable = Transformations.map(recentWatchlist) { it.isNotEmpty() }

        recentWatched = repository.fetchWatchedListMovies(recentCount)
        isWatchedListDataAvailable = Transformations.map(recentWatched) { it.isNotEmpty() }
    }

    fun fetchWatchedMovies(): LiveData<List<Movie>> {
        return recentWatched
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
        navigate(HomeFragmentDirections.actionWatchListFragment())
    }

}