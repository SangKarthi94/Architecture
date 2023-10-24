package com.architecture.data

import com.architecture.data.model.NewsArticle
import com.architecture.data.remote.service.NewsService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRemoteDataSource @Inject constructor(
    private val newsService: NewsService
) {

    suspend fun getNewsArticles(): List<NewsArticle> {
        return newsService.getNewsArticles().articles
    }
}
