

package com.app.assessment.test.movie.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.assessment.test.RetrofitBuilder
import com.app.assessment.test.movie.list.database.MovieDataBase
import com.app.assessment.test.movie.list.models.MovieItem
import com.app.assessment.test.movie.list.paginationv3.MovieItemMediator
import kotlinx.coroutines.flow.Flow

class MovieListViewModel: ViewModel() {

    fun getMovies(context: Context): Flow<PagingData<MovieItem>> {
        val pagingSource = {MovieDataBase.getInstance(context)?.movieItemDao()?.getMovies()!!}
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = pagingSource,
            remoteMediator = MovieItemMediator(RetrofitBuilder.moviesApiService, MovieDataBase.getInstance(context)!!)) .flow
    }
}