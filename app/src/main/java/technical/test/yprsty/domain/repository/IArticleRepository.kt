package technical.test.yprsty.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import technical.test.yprsty.domain.model.Article

interface IArticleRepository {
    fun getArticlesPaging(
        country: String = "id",
        category: String = "",
        query: String = ""
    ): Flow<PagingData<Article>>
}