package com.architecture.data.repository


import com.architecture.data.NewsRemoteDataSource
import com.architecture.data.model.NewsArticle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource
) {

    suspend fun getNewsArticles(): List<NewsArticle> {
        return newsRemoteDataSource.getNewsArticles()
    }
}
