package com.app.assessment.test.movie

import com.app.assessment.test.movie.list.models.MoviesResponse
import com.app.assessment.test.movie.list.models.video.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {

    @GET("movie/popular")
    suspend fun getMovies(@Query("api_key")apiKey: String, @Query("page")page: Int):MoviesResponse

    @GET("movie/{video_id}/videos")
    suspend fun getVideInformation(@Path("video_id")videId: Int, @Query("api_key")apiKey: String,): VideoResponse
}