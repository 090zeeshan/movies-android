package com.vd.movies.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.vd.movies.databinding.FragmentSearchBinding
import com.vd.movies.ui.base.BaseFragment
import com.vd.movies.ui.base.BaseViewModel
import com.vd.movies.ui.util.onDone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_search.*

@AndroidEntryPoint
class SearchFragment : BaseFragment(false) {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter

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

        moviesAdapter =
            MoviesAdapter(requireContext(), emptyList(), { viewModel.onItemClicked(it) })
        rvResult.adapter = moviesAdapter
        viewModel.moviesList.observe(viewLifecycleOwner, Observer { moviesAdapter.setData(it) })
        btnSearch.setOnClickListener { viewModel.onSearchClicked() }
        etSearch.onDone { viewModel.onSearchClicked() }
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }
}


