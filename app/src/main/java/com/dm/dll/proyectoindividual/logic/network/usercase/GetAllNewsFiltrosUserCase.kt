package com.dm.dll.proyectoindividual.logic.network.usercase

import android.util.Log
import com.dm.dll.proyectoindividual.core.Constants
import com.dm.dll.proyectoindividual.data.network.endpoints.news.NewsEndPoint
import com.dm.dll.proyectoindividual.data.network.endpoints.news.NewsFiltrosEndPoint
import com.dm.dll.proyectoindividual.data.network.entities.news.Article
import com.dm.dll.proyectoindividual.data.network.repository.RetrofitBase


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.Query

class GetAllNewsFiltrosUserCase {

    suspend fun invoke(q:String,  sortBy:String, limit: Int): Flow<Result<List<Article>>> = flow {
        var result: Result<List<Article>>? = null
        var newLimit=1

        val baseService = RetrofitBase.getNewsConnection()
        val service = baseService.create(NewsFiltrosEndPoint::class.java)

        while (newLimit<limit){
            //val call = service.getAllNews(q,sortBy,limit)
            val call = service.getAllNews(q, "2024-02-01", "2024-02-23", sortBy, limit)
            try {
                if (call.isSuccessful) {
                    val a = call.body()!!
                    val articulos = a.articles
                    result = Result.success(articulos!!)
                } else {
                    val msg = "Error en el llamado a la API de Jikan"
                    result = Result.failure(Exception(msg))
                    Log.d(Constants.TAG, msg)
                }
            } catch (ex: Exception) {
                Log.e(Constants.TAG, ex.stackTraceToString())
                result = Result.failure(ex)
            }

            emit(result!!)
            delay(20_000)
            //newLimit++
        }



    }


}