

package com.app.assessment.test.movie.list

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.assessment.test.RetrofitBuilder
import com.app.assessment.test.database.MovieDataBase
import com.app.assessment.test.models.movie.MovieItem
import com.app.assessment.test.movie.list.mediator.MovieItemMediator
import kotlinx.coroutines.flow.Flow

class MovieListViewModel: ViewModel() {

    var errorLiveData: MutableLiveData<String>? = null

    /**
     * This method will be used to pass PagingSource and remoteMediator to Pager
     *
     * @param context for accessing database.
     * @return Flow for observing data from database when data is updated.
     */
    fun getMovies(context: Context): Flow<PagingData<MovieItem>> {
        val pagingSource = {MovieDataBase.getInstance(context)?.movieItemDao()?.getMovies()!!}
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = pagingSource,
            remoteMediator = MovieItemMediator(RetrofitBuilder.moviesApiService, MovieDataBase.getInstance(context)!!).apply {
                errorLiveData = errorHandleLiveData
            }).flow
    }
}