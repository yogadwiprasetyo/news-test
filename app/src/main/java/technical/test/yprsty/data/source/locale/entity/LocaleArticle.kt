package technical.test.yprsty.data.source.locale.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class LocaleArticle(
    @PrimaryKey
    @ColumnInfo(name = "url_article")
    val urlArticle: String,
    val author: String?,
    val title: String?,
    val description: String?,
    @ColumnInfo(name = "url_image")
    val urlImage: String?,
    val published: String?,
    val content: String?,
)