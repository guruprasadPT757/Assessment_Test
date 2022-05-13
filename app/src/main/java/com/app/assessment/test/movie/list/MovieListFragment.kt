package com.app.assessment.test.movie.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.assessment.test.databinding.FragmentMovieListBinding


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
}