package com.example.skycatnewsapp.api

import com.example.skycatnewsapp.model.SampleStory
import com.example.skycatnewsapp.model.SkyCateNews
import retrofit2.http.GET
import retrofit2.http.Path

interface SkyCatNewsApi {
    @GET("news-list")
    suspend fun getNewsList(): SkyCateNews

    @GET("story/{id}")
    suspend fun getSampleStoryDetails(
        @Path("id") id: String
    ): SampleStory
}