package com.dm.dll.proyectoindividual.logic.network.usercase

import android.util.Log
import com.dm.dll.proyectoindividual.core.Constants
import com.dm.dll.proyectoindividual.data.network.endpoints.news.NewsEndPoint
import com.dm.dll.proyectoindividual.data.network.endpoints.news.TitularesEndPoint
import com.dm.dll.proyectoindividual.data.network.entities.news.Article
import com.dm.dll.proyectoindividual.data.network.repository.RetrofitBase


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllTitularesUserCase {

    suspend fun invoke(): Flow<Result<List<Article>>> = flow {
        var result: Result<List<Article>>? = null
        var newLimit=1

        val baseService = RetrofitBase.getNewsConnection()
        val service = baseService.create(TitularesEndPoint::class.java)

        while (true){
            val call = service.getAllTitulares()
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