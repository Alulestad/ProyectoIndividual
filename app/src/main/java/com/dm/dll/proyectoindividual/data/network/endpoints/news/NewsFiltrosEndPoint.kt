package com.dm.dll.proyectoindividual.data.network.endpoints.news



import com.dm.dll.proyectoindividual.data.network.entities.news.NewsX
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query

interface NewsFiltrosEndPoint {
    @GET("/v2/everything?q=applefrom=2024-02-01&to=2024-02-23")
    suspend fun getAllNews(@Query("q") q:String,@Query("sortBy") sortBy:String,@Query("pageSize")  pageSize:Int): Response<NewsX>


        @GET("/v2/everything")
        suspend fun getAllNews(
            @Query("q") q: String,
            @Query("from") from: String,
            @Query("to") to: String,
            @Query("sortBy") sortBy: String,
            @Query("pageSize") pageSize: Int
        ): Response<NewsX>


}
