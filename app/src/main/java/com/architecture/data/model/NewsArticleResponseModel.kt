package com.architecture.data.model

data class NewsArticleResponseModel(
    val articles: List<NewsArticle>
)

data class NewsArticle(
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?
)
