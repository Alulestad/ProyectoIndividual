package com.dm.dll.proyectoindividual.data.network.endpoints.news



import com.dm.dll.proyectoindividual.data.network.entities.news.NewsX
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query

interface TitularesEndPoint {
    @GET("/v2/top-headlines?country=us")
    suspend fun getAllTitulares(): Response<NewsX>

}
