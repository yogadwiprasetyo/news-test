package technical.test.yprsty.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import technical.test.yprsty.domain.model.Article

interface ArticleUseCase {
    fun fetchArticles(
        country: String = "id",
        category: String = "",
        query: String = ""
    ): Flow<PagingData<Article>>
}