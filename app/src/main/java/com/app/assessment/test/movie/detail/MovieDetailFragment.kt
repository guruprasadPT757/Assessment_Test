package com.app.assessment.test.movie.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.assessment.test.databinding.FragmentMovieDetailBinding
import com.app.assessment.test.movie.list.models.MovieItem

class MovieDetailFragment: Fragment() {

    private lateinit var movieDetailBinding: FragmentMovieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieDetailBinding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return movieDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieItem = arguments?.getParcelable<MovieItem>("movie_item")
        movieDetailBinding.movieItem = movieItem

        movieDetailBinding.btnWatchTrailer.setOnClickListener {

        }
    }
}