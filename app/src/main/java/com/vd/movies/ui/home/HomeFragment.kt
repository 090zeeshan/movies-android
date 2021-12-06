package com.vd.movies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnPreDraw
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.vd.movies.data.db.entity.Movie
import com.vd.movies.databinding.FragmentHomeBinding
import com.vd.movies.databinding.ItemMovieRecentBinding
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
    private lateinit var mBinding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var favoritesAdapter: MoviesAdapter
    private lateinit var watchedAdapter: MoviesAdapter
    private lateinit var watchlistAdapter: MoviesAdapter

    private fun onItemClicked(movie: Movie, binding: ViewDataBinding) {
        val resultBinding = binding as ItemMovieRecentBinding
        mNavExtras = FragmentNavigatorExtras(
            resultBinding.ivPoster to resultBinding.ivPoster.transitionName,
            resultBinding.tvTitle to resultBinding.tvTitle.transitionName,
            resultBinding.row to resultBinding.row.transitionName
        )
        viewModel.onItemClicked(movie)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.tag(TAG)
        Timber.i("onCreateView")

        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.viewModel = viewModel
        postponeEnterTransition()
        mBinding.root.doOnPreDraw {
            startPostponedEnterTransition()
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        btnSearch.setOnClickListener { viewModel.onSearchClicked() }
        etSearch.onDone { viewModel.onSearchClicked() }
        layoutFavoriteList.btnViewAll.setOnClickListener {
            setNavExtrasForRecents(mBinding.layoutFavoriteList.card)
            viewModel.onViewAllFavoriteClicked()
        }
        layoutWatchlist.btnViewAll.setOnClickListener {
            setNavExtrasForRecents(mBinding.layoutWatchlist.card)
            viewModel.onViewAllWatchlistClicked()
        }
        layoutWatchedList.btnViewAll.setOnClickListener {
            setNavExtrasForRecents(mBinding.layoutWatchedList.card)
            viewModel.onViewAllWatchedListClicked()
        }

        favoritesAdapter = MoviesAdapter(
            requireContext(),
            emptyList(),
            ::onItemClicked,
            MoviesAdapter.LayoutType.RECENT_ITEM
        )
        watchedAdapter = MoviesAdapter(
            requireContext(),
            emptyList(),
            ::onItemClicked,
            MoviesAdapter.LayoutType.RECENT_ITEM
        )
        watchlistAdapter = MoviesAdapter(
            requireContext(),
            emptyList(),
            ::onItemClicked,
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

    private fun setNavExtrasForRecents(layout: ConstraintLayout) {
        mNavExtras = FragmentNavigatorExtras(
            layout to layout.transitionName
        )
        Timber.i(layout.transitionName)
    }

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

}