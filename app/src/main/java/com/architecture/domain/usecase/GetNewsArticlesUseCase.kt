package com.architecture.domain.usecase


import com.architecture.data.model.NewsArticle
import com.architecture.data.repository.NewsRepository
import com.architecture.di.qualifier.IoDispatcher
import com.architecture.domain.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetNewsArticlesUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, List<NewsArticle>>(dispatcher) {

    override suspend fun execute(parameters: Unit): List<NewsArticle> {
        return newsRepository.getNewsArticles()
    }
}
