package com.app.assessment.test

import com.app.assessment.test.movie.MoviesApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val moviesApiService = retrofit.create(MoviesApiService::class.java)
}