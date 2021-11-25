package com.vd.movies.ui.details

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.vd.movies.R
import com.vd.movies.databinding.FragmentDetailsBinding
import com.vd.movies.ui.base.BaseFragment
import com.vd.movies.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*

@AndroidEntryPoint
class DetailsFragment : BaseFragment(false) {
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
        postponeEnterTransition()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnWatchList.setOnClickListener { viewModel.onAddToWatchListPressed() }
        btnWatched.setOnClickListener { viewModel.onAddToWatchedListPressed() }
        btnFavorite.setOnClickListener { viewModel.onAddToFavoritesPressed() }
        btnImdb.setOnClickListener { viewModel.onViewOnImdbPressed() }
        viewModel.movie.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                startPostponedEnterTransition()
            }
        })
    }

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

    companion object {
        @JvmStatic
        fun getRowTransitionName(imdId: String) = "row_$imdId"

        @JvmStatic
        fun getPosterTransitionName(imdId: String) = "poster_$imdId"

        @JvmStatic
        fun getTitleTransitionName(imdId: String) = "title_$imdId"

        @JvmStatic
        fun getTypeTransitionName(imdId: String) = "type_$imdId"

        @JvmStatic
        fun getYearTransitionName(imdId: String) = "year_$imdId"
    }
}