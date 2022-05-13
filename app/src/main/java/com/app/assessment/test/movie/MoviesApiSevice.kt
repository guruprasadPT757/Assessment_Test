package com.app.assessment.test.movie

import com.app.assessment.test.movie.list.models.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET("movie/popular")
    suspend fun getMovies(@Query("api_key")apiKey: String, @Query("page")page: Int):MoviesResponse
}