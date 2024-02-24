package com.dm.dll.proyectoindividual.data.network.endpoints.news



import com.dm.dll.proyectoindividual.data.network.entities.news.NewsX
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query

interface NewsEndPoint {
    @GET("/v2/everything?domains=techcrunch.com,thenextweb.com&pageSize=10")
    suspend fun getAllNews(): Response<NewsX>

}
