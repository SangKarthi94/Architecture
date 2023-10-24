package com.architecture.ui.newslist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appmattus.kotlinfixture.kotlinFixture
import com.architecture.data.model.NewsArticle
import com.architecture.domain.core.Result
import com.architecture.domain.usecase.GetNewsArticlesUseCase
import com.architecture.ui.newslist.mapper.NewsArticleUIMapper
import com.architecture.ui.newslist.model.NewsArticleUIModel
import com.freshworks.samplearchitectureapp.test.utils.MainCoroutineScopeRule
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class NewsListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    private val fixture = kotlinFixture()

    @Mock
    lateinit var mockGetNewsArticlesUseCase: GetNewsArticlesUseCase

    @Mock
    lateinit var mockNewsArticleUIMapper: NewsArticleUIMapper

    private fun createViewModel(): NewsListViewModel {
        return NewsListViewModel(
            getNewsArticlesUseCase = mockGetNewsArticlesUseCase,
            newsArticleUIMapper = mockNewsArticleUIMapper
        )
    }

    @org.junit.Test
    fun `Given UseCase returns Success result - When ViewModel is initialized - Then should emit success event`() =
        runTest {
            // Given
            val newsArticles = fixture<List<NewsArticle>>()
            val result = Result.Success(newsArticles)
            whenever(mockGetNewsArticlesUseCase.invoke(Unit)).thenReturn(result)

            val newsArticleUIModel = fixture<NewsArticleUIModel>()
            whenever(mockNewsArticleUIMapper.map(any())).thenReturn(newsArticleUIModel)

            // When
            val viewModel = createViewModel()

            // Then
            val expectedResult = NewsListViewModel.ViewState.Data(
                newsArticles = newsArticles.map { newsArticleUIModel }
            )
            Assert.assertThat(
                viewModel.viewState.value,
                equalTo(expectedResult)
            )
        }

    @org.junit.Test
    fun `Given UseCase returns Error result - When ViewModel is initialized - Then should emit error event`() =
        runTest {
            // Given
            val exception = Exception()
            val result = Result.Error(exception)
            whenever(mockGetNewsArticlesUseCase.invoke(Unit)).thenReturn(result)

            // When
            val viewModel = createViewModel()

            // Then
            val expectedResult = NewsListViewModel.ViewState.Error
            Assert.assertThat(
                viewModel.viewState.value,
                equalTo(expectedResult)
            )
        }
}