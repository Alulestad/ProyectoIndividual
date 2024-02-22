package com.dm.dll.proyectoindividual.logic.network.usercase

import com.dm.dll.proyectoindividual.data.network.entities.UserDB
import com.dm.dll.proyectoindividual.data.network.repositories.UsersRepository


class SaveUserInDBUserCase {
    suspend fun invoke(id: String, email: String, name: String): UserDB? {
        var us: UserDB? = null
        val a = UsersRepository().saveUserDB(id, email, name).getOrNull()
            /*.onSuccess {
                us = it
            }
            .onFailure {
                us = null
            }*/
        return a

    }
}