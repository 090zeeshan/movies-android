package com.vd.movies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vd.movies.data.api.Api
import com.vd.movies.data.db.AppDatabase
import com.vd.movies.data.db.entity.Movie
import com.vd.movies.data.repository.Repository
import com.vd.movies.databinding.FragmentHomeBinding
import com.vd.movies.ui.base.BaseFragment
import com.vd.movies.ui.base.BaseViewModel
import com.vd.movies.ui.search.MoviesAdapter
import com.vd.movies.ui.util.onDone
import com.zain.android.internetconnectivitylibrary.ConnectionUtil
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_listing.view.rvMovies
import kotlinx.android.synthetic.main.layout_recents_home.view.*
import kotlinx.android.synthetic.main.layout_search.*

class HomeFragment : BaseFragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoritesAdapter: MoviesAdapter
    private lateinit var watchedAdapter: MoviesAdapter
    private lateinit var watchlistAdapter: MoviesAdapter
    private val onItemClicked: (movie: Movie) -> Unit = {
        viewModel.onItemClicked(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = Repository.Builder(
            Api.Builder().build(),
            AppDatabase.getInstance(requireContext()),
            ConnectionUtil(requireContext())
        ).build()
        val factory = HomeViewModel.Factory(repository)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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