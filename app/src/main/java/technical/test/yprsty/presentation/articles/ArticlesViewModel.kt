package technical.test.yprsty.presentation.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import technical.test.yprsty.domain.model.Article
import technical.test.yprsty.domain.usecase.ArticleUseCase

class ArticlesViewModel(private val articleUseCase: ArticleUseCase) : ViewModel() {

    private val _uiState = MutableLiveData(ArticlesUiState(isLoading = true))
    val uiState: LiveData<ArticlesUiState> = _uiState

    fun loadArticles(category: String) {
        viewModelScope.launch(Dispatchers.Main) {
            articleUseCase.fetchArticles(category = category)
                .cachedIn(viewModelScope)
                .catch { e ->
                    e.printStackTrace()
                    _uiState.value = ArticlesUiState(userMessage = e.message.orEmpty())
                }
                .collect { pagingData ->
                    _uiState.value = ArticlesUiState(
                        isLoading = false,
                        userMessage = "",
                        items = pagingData
                    )
                }
        }
    }
}

data class ArticlesUiState(
    val isLoading: Boolean = false,
    val userMessage: String = "",
    val items: PagingData<Article> = PagingData.empty()
)