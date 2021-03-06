package com.app.assessment.test.movie.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.assessment.test.databinding.ItemMovieListBinding
import com.app.assessment.test.models.movie.MovieItem

class MovieListAdapter: PagingDataAdapter<MovieItem, MovieListAdapter.MovieViewHolder>(
    MovieComparator
) {

    var onItemClickListener: OnItemClickListener? = null

    inner class MovieViewHolder(var itemMovieListBinding: ItemMovieListBinding):
        RecyclerView.ViewHolder(itemMovieListBinding.root) {


        fun bindData(item: MovieItem) {
            itemMovieListBinding.movieItem = item
            itemMovieListBinding.onItemClick = onItemClickListener
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