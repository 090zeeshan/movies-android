package com.vd.movies.ui.details

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
    private lateinit var mBinding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        mBinding.viewModel = viewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
        postponeEnterTransition()

        btnWatchList.setOnClickListener { viewModel.onAddToWatchListPressed() }
        btnWatched.setOnClickListener { viewModel.onAddToWatchedListPressed() }
        btnFavorite.setOnClickListener { viewModel.onAddToFavoritesPressed() }
        btnImdb.setOnClickListener { viewModel.onViewOnImdbPressed() }
        viewModel.isContentVisible.observe(viewLifecycleOwner, Observer {
            if (it) {
                startInAnimations()
            }
        })
    }

    private fun startInAnimations() {
        Handler(Looper.getMainLooper()).postDelayed({
            startPostponedEnterTransition()
            mBinding.svPlot.startAnimation(
                AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.movie_plot_in
                )
            )
            mBinding.layoutMovieBtns.startAnimation(
                AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.movie_buttons_in
                )
            )
        }, 100)
    }

    override fun getViewModel(): BaseViewModel {
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