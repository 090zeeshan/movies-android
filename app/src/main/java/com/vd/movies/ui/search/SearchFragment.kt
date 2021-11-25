package com.vd.movies.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.vd.movies.data.db.entity.Movie
import com.vd.movies.databinding.FragmentSearchBinding
import com.vd.movies.databinding.ItemMovieResultBinding
import com.vd.movies.ui.base.BaseFragment
import com.vd.movies.ui.base.BaseViewModel
import com.vd.movies.ui.util.onDone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_search.*

@AndroidEntryPoint
class SearchFragment : BaseFragment(false) {
    private lateinit var mBinding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSearchBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.viewModel = viewModel

        postponeEnterTransition()
        mBinding.root.doOnPreDraw {
            startPostponedEnterTransition()
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesAdapter = MoviesAdapter(requireContext(), emptyList(), ::onItemPressed)
        rvResult.adapter = moviesAdapter
        viewModel.moviesList.observe(viewLifecycleOwner, Observer { moviesAdapter.setData(it) })
        btnSearch.setOnClickListener { viewModel.onSearchClicked() }
        etSearch.onDone { viewModel.onSearchClicked() }
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    private fun onItemPressed(movie: Movie, binding: ViewDataBinding) {
        val resultBinding = binding as ItemMovieResultBinding
        mNavExtras = FragmentNavigatorExtras(
            resultBinding.ivThumbnail to resultBinding.ivThumbnail.transitionName,
            resultBinding.tvTitle to resultBinding.tvTitle.transitionName,
            resultBinding.tvReleaseYear to resultBinding.tvReleaseYear.transitionName,
            resultBinding.tvType to resultBinding.tvType.transitionName,
            resultBinding.row to resultBinding.row.transitionName
        )
        viewModel.onItemClicked(movie)
    }
}


