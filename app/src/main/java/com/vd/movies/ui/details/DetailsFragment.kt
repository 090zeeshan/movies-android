package com.vd.movies.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.vd.movies.data.api.Api
import com.vd.movies.databinding.FragmentDetailsBinding
import com.vd.movies.data.repository.Repository
import com.vd.movies.data.db.AppDatabase
import com.vd.movies.ui.base.BaseFragment
import com.vd.movies.ui.base.BaseViewModel
import com.zain.android.internetconnectivitylibrary.ConnectionUtil
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : BaseFragment(false) {
    lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val imdbId = arguments?.let { DetailsFragmentArgs.fromBundle(it).imdbId } ?: ""
        val repository = Repository.Builder(
            Api.Builder().build(),
            AppDatabase.getInstance(requireContext()),
            ConnectionUtil(requireContext())
        ).build()
        val factory = DetailsViewModel.Factory(repository, imdbId)
        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)
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