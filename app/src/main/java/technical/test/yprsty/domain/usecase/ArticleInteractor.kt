package technical.test.yprsty.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import technical.test.yprsty.domain.model.Article
import technical.test.yprsty.domain.repository.IArticleRepository

class ArticleInteractor(private val repository: IArticleRepository) : ArticleUseCase {

    override fun fetchArticles(
        country: String,
        category: String,
        query: String
    ): Flow<PagingData<Article>> = repository.getArticlesPaging(country, category, query)

}