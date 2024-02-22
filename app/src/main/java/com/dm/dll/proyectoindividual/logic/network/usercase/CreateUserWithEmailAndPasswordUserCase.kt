package com.dm.dll.proyectoindividual.logic.network.usercase

import com.dm.dll.proyectoindividual.data.network.entities.UserDB
import com.dm.dll.proyectoindividual.data.network.repositories.AutenticationRepository


class CreateUserWithEmailAndPasswordUserCase {
    suspend fun invoke(email:String, password:String):UserDB?{
        var user: UserDB?=null
        AutenticationRepository().createUsersWithEmailAndPassword(email,password)
            .onSuccess {
                user =it
            }
            .onFailure {
                user=null
            }
        return user
    }
}