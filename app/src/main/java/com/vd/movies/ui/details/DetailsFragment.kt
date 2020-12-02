package com.vd.movies.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.vd.movies.R
import com.vd.movies.databinding.FragmentDetailsBinding
import com.vd.movies.repository.Repository
import com.vd.movies.ui.base.BaseFragment
import com.vd.movies.ui.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import timber.log.Timber

class DetailsFragment : BaseFragment(false) {
    lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        arguments?.let {
            val imdbId = DetailsFragmentArgs.fromBundle(it).imdbId
            viewModel.init(Repository(requireContext()),imdbId)
        }
    }

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
    }

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }
}