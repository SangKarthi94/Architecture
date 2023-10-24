package com.architecture.ui.newslist.mapper

import com.architecture.data.model.NewsArticle
import com.architecture.ui.newslist.model.NewsArticleUIModel
import javax.inject.Inject

class NewsArticleUIMapper @Inject constructor() {

    fun map(newsArticle: NewsArticle): NewsArticleUIModel {
        val uiModel = NewsArticleUIModel(
            title = newsArticle.title ?: "",
            description = newsArticle.description ?: "",
            image = newsArticle.urlToImage ?: ""
        )
        return uiModel
    }
}
