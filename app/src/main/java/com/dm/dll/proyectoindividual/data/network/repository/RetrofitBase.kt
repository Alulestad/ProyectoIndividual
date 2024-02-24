package com.dm.dll.proyectoindividual.data.network.repository

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBase {

    private const val NEWS_URL="https://newsapi.org/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("apiKey", "f577a37a66db41a289595d58f338ff65")
                .build()

            val requestBuilder = originalRequest.newBuilder()
                .url(url)

            val request = requestBuilder.build()
            chain.proceed(request)
        }
        .build()



    fun getNewsConnection():Retrofit{ //ESTO ES UNCAMENTE UNA CONNECCION
        // (menoa de la mitad de camino del diagrama)
        return Retrofit.Builder()
            .baseUrl(NEWS_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}