package com.dm.dll.proyectoindividual.logic.network.usercase

import com.dm.dll.proyectoindividual.data.network.repositories.AutenticationRepository


class DeleteUserWithEmailAndPasswordInAuthUserCase {
    suspend fun invoke(email:String, password:String):Void?{
        var user: Void?=null
        AutenticationRepository().deleteUser(email,password)
            .onSuccess {
                user =it
            }
            .onFailure {
                user=null
            }
        return user
    }
}