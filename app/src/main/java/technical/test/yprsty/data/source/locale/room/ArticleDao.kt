package technical.test.yprsty.data.source.locale.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import technical.test.yprsty.data.source.locale.entity.LocaleArticle

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<LocaleArticle>)

    @Query("SELECT * FROM article")
    fun pagingSourceAllArticle(): PagingSource<Int, LocaleArticle>

    @Query("DELETE FROM article")
    suspend fun clearAll()
}