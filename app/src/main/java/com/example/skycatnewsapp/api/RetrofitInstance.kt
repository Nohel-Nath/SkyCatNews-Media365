package com.example.skycatnewsapp.api

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    fun getRetrofit(context: Context): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(LocalJsonInterceptor(context))
            .build()

        return Retrofit.Builder()
            .baseUrl("http://local.fake/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApi(context: Context): SkyCatNewsApi {
        return getRetrofit(context).create(SkyCatNewsApi::class.java)
    }
}