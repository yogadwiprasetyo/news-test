package technical.test.yprsty.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class NetworkResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int,

	@field:SerializedName("articles")
	val articles: List<NetworkArticle>,

	@field:SerializedName("status")
	val status: String
)

data class NetworkArticle(

	@field:SerializedName("publishedAt")
	val publishedAt: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("urlToImage")
	val urlToImage: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("content")
	val content: String? = null
)