package com.app.assessment.test.movie.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.assessment.test.BuildConfig
import com.app.assessment.test.databinding.ItemMovieListBinding
import com.app.assessment.test.movie.list.models.MovieItem
import com.bumptech.glide.Glide

class MovieListAdapter(movieComparator: MovieComparator): PagingDataAdapter<MovieItem, MovieListAdapter.MovieViewHolder>(movieComparator) {

    inner class MovieViewHolder(var itemMovieListBinding: ItemMovieListBinding):
        RecyclerView.ViewHolder(itemMovieListBinding.root) {

        fun bindData(item: MovieItem) {
            itemMovieListBinding.movieItem = item
            Glide.with(itemMovieListBinding.ivMoviePoster.context)
                .load(BuildConfig.IMAGE_URL + item.posterPath)
                .into(itemMovieListBinding.ivMoviePoster)
        }
    }

    object MovieComparator : DiffUtil.ItemCallback<MovieItem>() {
        override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
            return oldItem == newItem
        }
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindData(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  MovieViewHolder (
                ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
}