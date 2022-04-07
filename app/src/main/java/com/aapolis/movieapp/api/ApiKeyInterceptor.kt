package com.aapolis.movieapp.api

import android.util.Log
import com.aapolis.movieapp.Constants
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url: HttpUrl = chain.request().url
        val newUrl = url.newBuilder().addQueryParameter("api_key", Constants.API_KEY).build()

        val newRequest = chain.request().newBuilder().url(newUrl).build()

        val requestWithHeader = chain.request().newBuilder().addHeader("Authorization", "skjldfasfkshf")

        Log.d("ApiKeyInterceptor", "intercept: new url = ${newUrl.toString()}")
        return chain.proceed(newRequest)
    }
}