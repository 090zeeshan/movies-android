package com.vd.movies.ui.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.vd.movies.databinding.FragmentListingBinding
import com.vd.movies.ui.base.BaseFragment
import com.vd.movies.ui.base.BaseViewModel
import com.vd.movies.ui.search.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_listing.*

@AndroidEntryPoint
class ListingFragment : BaseFragment() {
    private val viewModel: ListingViewModel by viewModels()
    private lateinit var adapter: MoviesAdapter

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