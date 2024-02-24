package com.dm.dll.proyectoindividual.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.dll.proyectoindividual.data.network.entities.news.Article
import com.dm.dll.proyectoindividual.logic.network.usercase.GetAllNewsFiltrosUserCase
import com.dm.dll.proyectoindividual.logic.network.usercase.GetAllNewsUserCase
import com.dm.dll.proyectoindividual.logic.network.usercase.GetAllTitularesUserCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoticiasFiltradasViewModel : ViewModel() {

    val listItems = MutableLiveData<List<Article>>()
    val error = MutableLiveData<String>()

    fun getAllNewsFiltradas(tema:String,ordena:String,limite:Int) { //Aca yo hago uncamenta la consulta en el IO
        viewModelScope.launch(Dispatchers.IO) {
            val userCase = GetAllNewsFiltrosUserCase()
            //val newsFlow = userCase.invoke(tema,ordena,limite)
            val newsFlow = userCase.invoke(tema,ordena,limite)

            newsFlow
                .collect { article -> //recolecta lo de la tuberia
                    article.onSuccess {
                        listItems.postValue(it.toList())
                    }
                    article.onFailure {
                        error.postValue(it.message.toString())
                    }

                }

        }
    }




}