package com.dm.dll.proyectoindividual.logic.network.usercase

import com.dm.dll.proyectoindividual.data.network.repositories.UsersRepository


class DeleteUserInDBByNameUserCase {
    suspend fun invoke(name: String): Void? {

        val a = UsersRepository().deleteUserByNameFromDB(name).getOrNull()

        return null

    }
}