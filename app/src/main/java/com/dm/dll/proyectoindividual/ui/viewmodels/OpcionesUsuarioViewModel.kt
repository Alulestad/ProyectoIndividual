package com.dm.dll.proyectoindividual.ui.viewmodels;


import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.biometric.BiometricManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.dll.proyectoindividual.data.network.entities.UserDB
import com.dm.dll.proyectoindividual.logic.network.usercase.CreateUserWithEmailAndPasswordUserCase
import com.dm.dll.proyectoindividual.logic.network.usercase.DeleteUserInDBByNameUserCase
import com.dm.dll.proyectoindividual.logic.network.usercase.DeleteUserInDBUserCase
import com.dm.dll.proyectoindividual.logic.network.usercase.DeleteUserWithEmailAndPasswordInAuthUserCase
import com.dm.dll.proyectoindividual.logic.network.usercase.SaveUserInDBUserCase
import com.dm.dll.proyectoindividual.logic.network.usercase.SingInUserWithEmailAndPasswordUserCase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OpcionesUsuarioViewModel: ViewModel() {

    val user get() = _user
    private val _user = MutableLiveData<UserDB>()

    val error get()=_error
    private val _error = MutableLiveData<String>()


    fun deleteUserWithEmailAndPassword(email:String,password:String,user:String){
        viewModelScope.launch(Dispatchers.IO) {
            val us =
                DeleteUserWithEmailAndPasswordInAuthUserCase().invoke(email,password)
            if(us!=null){
                //val a=DeleteUserInDBByNameUserCase.
                //_user.postValue(newUs!!)
            }else{
                _error.postValue("Ocurrio un error")

                
            }


        }
    }

}