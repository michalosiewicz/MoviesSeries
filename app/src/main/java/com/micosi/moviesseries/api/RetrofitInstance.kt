package com.micosi.moviesseries.api

import com.micosi.moviesseries.api.services.MoviesService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }.build()

    val retrofit: MoviesService by lazy {
        Retrofit.Builder()
            .baseUrl("https://movies-app1.p.rapidapi.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesService::class.java)
    }
}