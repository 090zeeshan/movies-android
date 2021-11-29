package com.vd.movies.ui.listing

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.vd.movies.R
import com.vd.movies.data.db.entity.Movie
import com.vd.movies.databinding.FragmentListingBinding
import com.vd.movies.databinding.ItemMovieResultBinding
import com.vd.movies.ui.base.BaseFragment
import com.vd.movies.ui.base.BaseViewModel
import com.vd.movies.ui.search.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_listing.*
import timber.log.Timber

@AndroidEntryPoint
class ListingFragment : BaseFragment() {
    private lateinit var mBinding: FragmentListingBinding
    private val viewModel: ListingViewModel by viewModels()
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentListingBinding.inflate(inflater, container, false)
        mBinding.viewModel = viewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
        Timber.i(mBinding.container.transitionName)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MoviesAdapter(
            requireContext(),
            emptyList(),
            ::onItemPressed
        )
        rvMovies.adapter = adapter
        viewModel.movies.observe(viewLifecycleOwner, Observer { adapter.setData(it) })
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
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

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

    companion object {
        @JvmStatic
        fun getTransitionName(title: String) = "recents_$title"
    }
}