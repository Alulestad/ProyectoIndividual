package com.dm.dll.proyectoindividual.ui.viewmodels

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.dll.proyectoindividual.core.Constants
import com.dm.dll.proyectoindividual.data.network.entities.UserDB
import com.dm.dll.proyectoindividual.data.network.entities.news.Article
import com.dm.dll.proyectoindividual.logic.network.usercase.CreateUserWithEmailAndPasswordUserCase
import com.dm.dll.proyectoindividual.logic.network.usercase.GetAllNewsUserCase
import com.dm.dll.proyectoindividual.logic.network.usercase.SaveUserInDBUserCase
import com.dm.dll.proyectoindividual.logic.network.usercase.SingInUserWithEmailAndPasswordUserCase
import com.dm.dll.proyectoindividual.ui.activities.NoticiasFiltradasActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormularioNoticiasFiltradasViewModel : ViewModel() {

    val filtros get() = _filtros
    private val _filtros = MutableLiveData<String>()

    val error get()=_error
    private val _error = MutableLiveData<String>()


    fun createCadenaStrings(tema:String,orden:String,limite:String){
        viewModelScope.launch(Dispatchers.IO) {
            //val us = CreateUserWithEmailAndPasswordUserCase().invoke(email,password)
            //if(us!=null){
                //val newUs= SaveUserInDBUserCase().invoke(us.id,us.email,user)
            try {
                _filtros.postValue(tema)
                _filtros.postValue(orden)
                _filtros.postValue(limite)
            }catch (ex:Exception){
                Log.d(Constants.TAG,ex.toString())
            }

            //}else{
                _error.postValue("Ocurrio un error")


            //}


        }
    }




}