package com.vd.movies.ui.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vd.movies.databinding.FragmentListingBinding
import com.vd.movies.data.repository.Repository
import com.vd.movies.data.api.Api
import com.vd.movies.data.db.AppDatabase
import com.vd.movies.ui.base.BaseFragment
import com.vd.movies.ui.base.BaseViewModel
import com.vd.movies.ui.search.MoviesAdapter
import com.zain.android.internetconnectivitylibrary.ConnectionUtil
import kotlinx.android.synthetic.main.fragment_listing.*

class ListingFragment : BaseFragment() {
    private lateinit var viewModel: ListingViewModel
    private lateinit var adapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listingType =
            arguments?.let { ListingFragmentArgs.fromBundle(it).listingType }
                ?: ListingType.WATCHLIST
        viewModel = ViewModelProvider(this).get(ListingViewModel::class.java)
        viewModel.init(
            Repository(
                Api(),
                AppDatabase.getInstance(requireContext()),
                ConnectionUtil(requireContext())
            ),
            listingType
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentListingBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MoviesAdapter(requireContext(), emptyList(), { viewModel.onItemClicked(it) })
        rvMovies.adapter = adapter
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }
}