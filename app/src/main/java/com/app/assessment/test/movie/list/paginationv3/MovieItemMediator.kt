@file:OptIn(ExperimentalPagingApi::class)

package com.app.assessment.test.movie.list.paginationv3

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.app.assessment.test.BuildConfig
import com.app.assessment.test.movie.MoviesApiService
import com.app.assessment.test.movie.list.database.MovieDataBase
import com.app.assessment.test.movie.list.models.MovieItem
import com.app.assessment.test.movie.list.models.RemoteKeys
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieItemMediator(private val apiService: MoviesApiService,
                        private val movieDb: MovieDataBase
): RemoteMediator<Int, MovieItem>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieItem>
    ): MediatorResult {
        val key  = when(loadType) {
            LoadType.APPEND -> {
                getKey()
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.REFRESH -> {
                if (movieDb.movieItemDao().count() > 0) return MediatorResult.Success(false)
                null
            }
        }
        return try {
            if (key != null) {
                if (key.isEndReached) return MediatorResult.Success(endOfPaginationReached = true)
            }

            val page: Int = key?.nextKey ?: 1
            val response = apiService.getMovies(BuildConfig.API_KEY, page)
            val isEndOfPageReached = response.results?.isEmpty() == true
            CoroutineScope(Dispatchers.IO).launch {
                movieDb.withTransaction {
                    val nextKey = page + 1
                    movieDb.remoteKeyDao().insertKey(
                        RemoteKeys(
                            0,
                            nextKey = nextKey,
                            isEndReached = isEndOfPageReached
                        )
                    )
                }
                response.results?.let {
                    movieDb.movieItemDao().insertAll(it)
                }
            }
            MediatorResult.Success(isEndOfPageReached)
        } catch (exp : Exception) {
            MediatorResult.Error(exp)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    private suspend fun getKey(): RemoteKeys? {
        return movieDb.remoteKeyDao().getKeys().firstOrNull()
    }
}