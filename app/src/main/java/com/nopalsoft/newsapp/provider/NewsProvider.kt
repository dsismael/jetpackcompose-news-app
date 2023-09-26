package com.nopalsoft.newsapp.provider

import com.nopalsoft.newsapp.model.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "fe050c83e88a4c9d93e6bff7842a1da1"

interface NewsProvider {

    @GET("top-headlines?apiKey=$API_KEY")
    suspend fun topHeadLines(
        @Query("country") country: String = "us",
        @Query("page") pageNumber: Int = 1 //provisional
    ): Response<NewsApiResponse>

    @GET("everything?apiKey=$API_KEY")
    suspend fun searchForNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int = 1 //provisional
    ): Response<NewsApiResponse>
}