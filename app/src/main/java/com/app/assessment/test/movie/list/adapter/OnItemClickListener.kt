package com.app.assessment.test.movie.list.adapter

import com.app.assessment.test.movie.list.models.MovieItem

interface OnItemClickListener {
    fun onItemClick(item: MovieItem)
}