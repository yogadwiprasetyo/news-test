package technical.test.yprsty.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val urlArticle: String,
    val author: String?,
    val title: String?,
    val description: String?,
    val urlImage: String?,
    val publishedAt: String?,
    val contentPreview: String?,
): Parcelable