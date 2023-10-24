package com.architecture.ui.newslist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architecture.domain.core.Result
import com.architecture.domain.usecase.GetNewsArticlesUseCase
import com.architecture.ui.newslist.mapper.NewsArticleUIMapper
import com.architecture.ui.newslist.model.NewsArticleUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getNewsArticlesUseCase: GetNewsArticlesUseCase,
    private val newsArticleUIMapper: NewsArticleUIMapper
) : ViewModel() {

    sealed class ViewState(){
        object Loading : ViewState()
        data class Data(val newsArticles: List<NewsArticleUIModel>) : ViewState()
        object Error : ViewState()
    }

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    init {
        getNewsArticles()
    }

    private fun getNewsArticles() {

        _viewState.value = ViewState.Loading

        viewModelScope.launch {

            when (val result = getNewsArticlesUseCase(Unit)){
                is Result.Error -> {
                    _viewState.value = ViewState.Error
                }
                Result.Loading -> {
                    _viewState.value = ViewState.Loading
                }
                is Result.Success -> {
                    val uiModels = result.data.map { newsArticleUIMapper.map(it) }
                    _viewState.value = ViewState.Data(uiModels)
                }
            }
        }
    }
}