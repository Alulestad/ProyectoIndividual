package com.dm.dll.proyectoindividual.data.network.entities.news

data class NewsX(
    val articles: List<Article> = listOf(),
    val status: String = "",
    val totalResults: Int = 0
)