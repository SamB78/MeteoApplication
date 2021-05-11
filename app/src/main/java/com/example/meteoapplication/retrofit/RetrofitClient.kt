package com.example.meteoapplication.retrofit

import com.example.meteoapplication.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val MainUrl = "http://api.openweathermap.org/data/2.5/"

    private val retrofitClient: Retrofit.Builder by lazy {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


        val okhttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(TokenInterceptor())

        Retrofit.Builder()
            .baseUrl(MainUrl)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: ApiInterface by lazy {
        retrofitClient
            .build()
            .create(ApiInterface::class.java)
    }
}

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val token = "6820fcda05c55b7b2733ea210e13b0a9"
        val url = original.url.newBuilder().addQueryParameter("appid", token).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}