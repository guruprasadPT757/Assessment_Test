package com.app.assessment.test.movie.list

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.app.assessment.test.Preference
import com.app.assessment.test.R
import com.app.assessment.test.databinding.FragmentMovieListBinding
import com.app.assessment.test.movie.list.adapter.MovieListAdapter
import com.app.assessment.test.movie.list.adapter.OnItemClickListener
import com.app.assessment.test.models.movie.MovieItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieListFragment: Fragment() {
    private lateinit var homeBinding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentMovieListBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MovieListAdapter()
        adapter.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(view: View, item: MovieItem) {
                val bundle = bundleOf("movie_item" to item)
                val imageView = view.findViewById<ImageView>(R.id.ivMoviePoster)
                val extras = FragmentNavigatorExtras(
                    imageView to item.posterPath!!
                )
                findNavController().navigate(
                    R.id.action_homeFragment_to_movieDetailFragment,
                    bundle,
                    null,
                    extras
                )
            }
        }
        homeBinding.rvMoviesList.adapter = adapter

        val viewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getMovies(requireContext())
                .collectLatest { movies ->
                    adapter.submitData(movies)
                }
        }

        homeBinding.swipeToRefresh.setOnRefreshListener {
            adapter.notifyDataSetChanged()
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getMovies(requireContext()).collectLatest { movies ->
                    if (homeBinding.swipeToRefresh.isRefreshing) {
                        homeBinding.swipeToRefresh.isRefreshing = false
                    }
                    adapter.submitData(movies)
                }
            }
        }

        viewModel.errorLiveData?.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        val comment = Preference(requireContext()).getString(Preference.MOVIE_COMMENTS)
        if (comment?.isNotEmpty() == true) {
            showToast(comment)
            Preference(requireContext()).putString(Preference.MOVIE_COMMENTS, "")
        }
    }
}

fun Fragment.showToast(message: String) {
    val view = LayoutInflater.from(requireContext()).inflate(R.layout.layout_toast, null, false)
    val toast = Toast(view.context)
    toast.duration = Toast.LENGTH_LONG
    toast.setGravity(Gravity.BOTTOM, 0, 100)
    view.findViewById<TextView>(R.id.toastTextView).text = message
    toast.view = view
    toast.show()
}

fun Fragment.showSnackBar(it: String) {
    Snackbar.make(requireContext(), view!!, it, Snackbar.LENGTH_LONG).apply {
        setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.flush_mahogany))
    }.show()
}