package technical.test.yprsty.utils

import technical.test.yprsty.data.source.locale.entity.LocaleArticle
import technical.test.yprsty.data.source.remote.response.NetworkArticle
import technical.test.yprsty.domain.model.Article

// Network to Local
fun NetworkArticle.toLocal() = LocaleArticle(
    urlArticle = url.orEmpty(),
    author = author,
    title = title,
    description = description,
    urlImage = urlToImage,
    published = publishedAt,
    content = content
)

// Note: JvmName is used to provide a unique name for each extension function with the same name.
// Without this, type erasure will cause compiler errors because these methods will have the same
// signature on the JVM.
@JvmName("networkToLocal")
fun List<NetworkArticle>.toLocal() = map(NetworkArticle::toLocal)

// Local to External
fun LocaleArticle.toExternal() = Article(
    urlArticle = urlArticle,
    author = author,
    title = title,
    description = description,
    urlImage = urlImage,
    publishedAt = published,
    contentPreview = content
)

@JvmName("localToExternal")
fun List<LocaleArticle>.toExternal() = map(LocaleArticle::toExternal)
