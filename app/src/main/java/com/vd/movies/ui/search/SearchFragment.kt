package com.vd.movies.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vd.movies.R
import com.vd.movies.databinding.FragmentSearchBinding
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_search.*
import timber.log.Timber

class SearchFragment : Fragment() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        arguments?.let {
            viewModel.init(SearchFragmentArgs.fromBundle(it).searchKey)
        }

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

        moviesAdapter = MoviesAdapter(requireContext(), emptyList())
        rvResult.adapter = moviesAdapter
        viewModel.moviesList.observe(viewLifecycleOwner, Observer {
            moviesAdapter.list = it
            moviesAdapter.notifyDataSetChanged()
        })
        btnSearch.setOnClickListener{
            viewModel.onSearchPressed()
        }
    }
}


