package technical.test.yprsty.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import technical.test.yprsty.data.source.locale.entity.LocaleArticle
import technical.test.yprsty.data.source.locale.entity.LocaleRemoteKeys
import technical.test.yprsty.data.source.locale.room.NewsDatabase
import technical.test.yprsty.data.source.remote.service.ApiService
import technical.test.yprsty.utils.toLocal

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val database: NewsDatabase,
    private val apiService: ApiService,
    private val country: String = "id",
    private val category: String = "",
    private val query: String = ""
) : RemoteMediator<Int, LocaleArticle>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocaleArticle>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiKey = "" // TODO: Get API Key from BuildConfig.apiKey
            val response = apiService.getTopHeadlines(
                apiKey = apiKey,
                page = page,
                pageSize = state.config.pageSize,
                country = country,
                category = category,
                query = query,
            )
            val endOfPaginationReached = response.articles.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.articleDao().clearAll()
                    database.remoteKeysDao().deleteRemoteKeys()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = response.articles.map {
                    LocaleRemoteKeys(id = it.url.orEmpty(), prevKey = prevKey, nextKey = nextKey)
                }

                database.remoteKeysDao().insertAll(keys)
                database.articleDao().insertArticles(response.articles.toLocal())
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, LocaleArticle>): LocaleRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.urlArticle)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, LocaleArticle>): LocaleRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.urlArticle)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, LocaleArticle>): LocaleRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.urlArticle?.let { url ->
                database.remoteKeysDao().getRemoteKeysId(url)
            }
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}