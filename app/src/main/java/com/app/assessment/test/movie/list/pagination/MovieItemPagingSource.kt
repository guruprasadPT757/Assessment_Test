package com.app.assessment.test.movie.list.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.assessment.test.BuildConfig
import com.app.assessment.test.RetrofitBuilder
import com.app.assessment.test.movie.list.models.MovieItem
import com.app.assessment.test.movie.list.models.MoviesResponse
import retrofit2.HttpException
import java.io.IOException

class MovieItemPagingSource: PagingSource<Int, MovieItem>() {
    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        val page = params.key ?: 1

        try {
            val movieResponse: MoviesResponse =
                RetrofitBuilder.moviesApiService.getMovies(BuildConfig.API_KEY, page)

            val nextKey = if (movieResponse.results?.isNotEmpty() == true) {
                null
            } else {
                page + (params.loadSize / 20)
            }
            return LoadResult.Page(
                data = movieResponse.results ?: listOf(),
                prevKey = if (page == 1) null else page,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }
}