package technical.test.yprsty.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import technical.test.yprsty.MainDispatcherRule
import technical.test.yprsty.domain.model.Article
import technical.test.yprsty.domain.repository.IArticleRepository

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ArticleUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var articleRepository: IArticleRepository
    private lateinit var articleUseCase: ArticleUseCase

    @Before
    fun setup() {
        articleUseCase = ArticleInteractor(articleRepository)
    }

    @Test
    fun `should fetchArticles paging data from repository`() {
        val dummyResult = flowOf(PagingData.from(listOf<Article>()))
        `when`(articleRepository.getArticlesPaging()).thenReturn(dummyResult)
        articleUseCase.fetchArticles()

        verify(articleRepository).getArticlesPaging()
        verifyNoMoreInteractions(articleRepository)
    }
}