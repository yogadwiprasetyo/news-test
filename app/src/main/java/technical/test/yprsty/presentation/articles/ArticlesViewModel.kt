package technical.test.yprsty.presentation.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import technical.test.yprsty.domain.model.Article
import technical.test.yprsty.domain.usecase.ArticleUseCase
import technical.test.yprsty.utils.Async

class ArticlesViewModel(private val articleUseCase: ArticleUseCase) : ViewModel() {

    private val _userMessage = MutableStateFlow("")
    private val _isLoading = MutableStateFlow(false)
    private val _articlesAsync = articleUseCase.fetchArticles()
        .map { Async.Success(it) }
        .catch<Async<PagingData<Article>>> { emit(Async.Error("Failed fetch data") ) }

    val uiState: StateFlow<ArticlesUiState> = combine(
        _isLoading, _userMessage, _articlesAsync
    ) { isLoading, userMessage, articles ->
        when (articles) {
            Async.Loading -> {
                ArticlesUiState(isLoading = true)
            }
            is Async.Error -> {
                ArticlesUiState(userMessage = articles.errorMessage)
            }
            is Async.Success -> {
                ArticlesUiState(
                    items = articles.data,
                    isLoading = isLoading,
                    userMessage = userMessage
                )
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ArticlesUiState(isLoading = true)
        )
}

data class ArticlesUiState(
    val isLoading: Boolean = false,
    val userMessage: String = "",
    val items: PagingData<Article> = PagingData.empty()
)