package com.vd.movies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.vd.movies.data.db.entity.Movie
import com.vd.movies.databinding.FragmentHomeBinding
import com.vd.movies.ui.base.BaseFragment
import com.vd.movies.ui.base.BaseViewModel
import com.vd.movies.ui.search.MoviesAdapter
import com.vd.movies.ui.util.onDone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_listing.view.rvMovies
import kotlinx.android.synthetic.main.layout_recents_home.view.*
import kotlinx.android.synthetic.main.layout_search.*
import timber.log.Timber

private const val TAG = "HomeF"

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var favoritesAdapter: MoviesAdapter
    private lateinit var watchedAdapter: MoviesAdapter
    private lateinit var watchlistAdapter: MoviesAdapter

    private val onItemClicked: (movie: Movie) -> Unit = {
        viewModel.onItemClicked(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.tag(TAG)
        Timber.i( "onCreateView")

        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i( "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        btnSearch.setOnClickListener { viewModel.onSearchClicked() }
        etSearch.onDone { viewModel.onSearchClicked() }
        layoutFavoriteList.btnViewAll.setOnClickListener { viewModel.onViewAllFavoriteClicked() }
        layoutWatchlist.btnViewAll.setOnClickListener { viewModel.onViewAllWatchlistClicked() }
        layoutWatchedList.btnViewAll.setOnClickListener { viewModel.onViewAllWatchedListClicked() }

        favoritesAdapter = MoviesAdapter(
            requireContext(),
            emptyList(),
            onItemClicked,
            MoviesAdapter.LayoutType.RECENT_ITEM
        )
        watchedAdapter = MoviesAdapter(
            requireContext(),
            emptyList(),
            onItemClicked,
            MoviesAdapter.LayoutType.RECENT_ITEM
        )
        watchlistAdapter = MoviesAdapter(
            requireContext(),
            emptyList(),
            onItemClicked,
            MoviesAdapter.LayoutType.RECENT_ITEM
        )

        layoutFavoriteList.rvMovies.adapter = favoritesAdapter
        layoutWatchedList.rvMovies.adapter = watchedAdapter
        layoutWatchlist.rvMovies.adapter = watchlistAdapter

        viewModel.recentFavorites.observe(viewLifecycleOwner, Observer {
            favoritesAdapter.setData(it)
        })
        viewModel.recentWatched.observe(viewLifecycleOwner, Observer {
            watchedAdapter.setData(it)
        })
        viewModel.recentWatchlist.observe(viewLifecycleOwner, Observer {
            watchlistAdapter.setData(it)
        })
    }

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

}