package com.architecture.data.remote.service


import com.architecture.data.model.NewsArticleResponseModel
import retrofit2.http.GET

interface NewsService {

    @GET("v2/top-headlines?country=us&apiKey=d6c6c7358b70483a8625ab5aef41d5f3")
    suspend fun getNewsArticles(): NewsArticleResponseModel
}
