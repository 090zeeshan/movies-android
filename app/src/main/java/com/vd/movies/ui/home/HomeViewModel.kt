package com.vd.movies.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vd.movies.data.model.Movie
import com.vd.movies.data.repository.IRepository
import com.vd.movies.data.repository.Repository
import com.vd.movies.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel("Movies") {
    private val recentCount = 5;
    val searchKey = MutableLiveData("")
    val recentFavorites = MutableLiveData(emptyList<Movie>())
    val recentWatched = MutableLiveData(emptyList<Movie>())
    val recentWatchlist = MutableLiveData(emptyList<Movie>())
    val isFavsDataAvailable = MutableLiveData(false)
    val isWatchlistDataAvailable = MutableLiveData(false)
    val isWatchedListDataAvailable = MutableLiveData(false)

    fun init(repository: IRepository){
        this.repository = repository
    }

    fun fetchRecentMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            val favs = repository.fetchFavoriteMovies(recentCount)
            val watched = repository.fetchWatchedListMovies(recentCount)
            val watchlist = repository.fetchWatchlistMovies(recentCount)

            recentFavorites.postValue(favs)
            recentWatched.postValue(watched)
            recentWatchlist.postValue(watchlist)

            isFavsDataAvailable.postValue(favs.isNotEmpty())
            isWatchedListDataAvailable.postValue(watched.isNotEmpty())
            isWatchlistDataAvailable.postValue(watchlist.isNotEmpty())
        }
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