package com.micosi.moviesseries.api

import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-RapidAPI-Host", "movies-app1.p.rapidapi.com")
            .addHeader("X-RapidAPI-Key", "f2e1625024msh74dcb43b6f974d2p1ee608jsn18d754f59359")
            .build()

        return chain.proceed(request)
    }
}