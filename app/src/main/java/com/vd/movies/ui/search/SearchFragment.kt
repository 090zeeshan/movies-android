package com.vd.movies.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vd.movies.databinding.FragmentSearchBinding
import com.vd.movies.data.repository.Repository
import com.vd.movies.data.api.Api
import com.vd.movies.data.db.AppDatabase
import com.vd.movies.ui.base.BaseFragment
import com.vd.movies.ui.base.BaseViewModel
import com.vd.movies.ui.util.onDone
import com.zain.android.internetconnectivitylibrary.ConnectionUtil
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_search.*
import timber.log.Timber

class SearchFragment : BaseFragment(false) {
    private lateinit var viewModel: SearchViewModel
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val searchKey = arguments?.let { SearchFragmentArgs.fromBundle(it).searchKey } ?: ""
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.init(
            Repository(
                Api(),
                AppDatabase.getInstance(requireContext()),
                ConnectionUtil(requireContext())
            ),
            searchKey
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesAdapter = MoviesAdapter(requireContext(), emptyList(), { viewModel.onItemClicked(it) })
        rvResult.adapter = moviesAdapter
        viewModel.moviesList.observe(viewLifecycleOwner, Observer {
            moviesAdapter.list = it
            moviesAdapter.notifyDataSetChanged()
        })
        btnSearch.setOnClickListener { viewModel.onSearchClicked() }
        etSearch.onDone { viewModel.onSearchClicked() }
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }
}


