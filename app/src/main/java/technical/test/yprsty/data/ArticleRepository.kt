package technical.test.yprsty.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import technical.test.yprsty.data.paging.ArticleRemoteMediator
import technical.test.yprsty.data.source.locale.room.NewsDatabase
import technical.test.yprsty.data.source.remote.service.ApiService
import technical.test.yprsty.domain.model.Article
import technical.test.yprsty.domain.repository.IArticleRepository
import technical.test.yprsty.utils.toExternal

class ArticleRepository(
    private val newsDatabase: NewsDatabase,
    private val apiService: ApiService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IArticleRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getArticlesPaging(
        country: String,
        category: String,
        query: String
    ): Flow<PagingData<Article>> = Pager(
        config = PagingConfig(
            pageSize = 5
        ),
        remoteMediator = ArticleRemoteMediator(
            newsDatabase,
            apiService,
            country,
            category,
            query
        ),
        pagingSourceFactory = {
            newsDatabase.articleDao().pagingSourceAllArticle()
        }
    ).flow.map {
        it.map { locale -> locale.toExternal() }
    }.flowOn(defaultDispatcher)

}