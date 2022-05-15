package com.app.assessment.test.movie.list.adapter

import android.view.View
import com.app.assessment.test.models.movie.MovieItem

interface OnItemClickListener {
    fun onItemClick(view: View, item: MovieItem)
}