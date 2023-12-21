package technical.test.yprsty.data.source.remote.service

import retrofit2.http.GET
import retrofit2.http.Query
import technical.test.yprsty.data.source.remote.response.NetworkResponse

interface ApiService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("country") country: String = "us",
        @Query("category") category: String = "",
        @Query("q") query: String = "",
    ): NetworkResponse

}