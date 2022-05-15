package com.app.assessment.test

import com.app.assessment.test.movie.MoviesApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This class is building MoviesApiService for calling API's using retrofit library.
 *
 */
object RetrofitBuilder {
    val moviesApiService: MoviesApiService
    init {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        moviesApiService = retrofit.create(MoviesApiService::class.java)
    }

}