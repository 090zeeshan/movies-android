package com.vd.movies.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnWatchList.setOnClickListener { viewModel.onAddToWatchListPressed() }
        btnWatched.setOnClickListener { viewModel.onAddToWatchedListPressed() }
        btnFavorite.setOnClickListener { viewModel.onAddToFavoritesPressed() }
        btnImdb.setOnClickListener { viewModel.onViewOnImdbPressed() }
    }

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }
}