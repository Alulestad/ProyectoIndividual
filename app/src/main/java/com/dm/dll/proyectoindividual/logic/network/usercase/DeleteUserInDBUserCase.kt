package com.dm.dll.proyectoindividual.logic.network.usercase

import com.dm.dll.proyectoindividual.data.network.entities.UserDB
import com.dm.dll.proyectoindividual.data.network.repositories.UsersRepository


class DeleteUserInDBUserCase {
    suspend fun invoke(id: String): Void? {

        val a = UsersRepository().deleteUserFromDB(id).getOrNull()

        return a

    }
}