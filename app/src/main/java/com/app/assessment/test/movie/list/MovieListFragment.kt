package com.app.assessment.test.movie.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.assessment.test.R
import com.app.assessment.test.databinding.FragmentMovieListBinding
import com.app.assessment.test.movie.list.adapter.MovieListAdapter
import com.app.assessment.test.movie.list.adapter.OnItemClickListener
import com.app.assessment.test.movie.list.models.MovieItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MovieListFragment: Fragment() {
    private lateinit var homeBinding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding= FragmentMovieListBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MovieListAdapter()
        adapter.onItemClickListener = object: OnItemClickListener {
            override fun onItemClick(item: MovieItem) {
                val bundle = bundleOf("movie_item" to item)
                findNavController().navigate(R.id.action_homeFragment_to_movieDetailFragment, bundle)
            }
        }
        homeBinding.rvMoviesList.adapter = adapter

        val viewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getMovies(requireContext()).collectLatest { movies ->
                adapter.submitData(movies)
            }
        }


        homeBinding.swipeToRefresh.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getMovies(requireContext()).collectLatest { movies ->
                    if (homeBinding.swipeToRefresh.isRefreshing) {
                        homeBinding.swipeToRefresh.isRefreshing = false
                    }
                    adapter.submitData(movies)
                }
            }
        }
    }
}