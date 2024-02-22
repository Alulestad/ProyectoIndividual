package com.dm.dll.proyectoindividual.logic.network.usercase

import com.dm.dll.proyectoindividual.data.network.entities.UserDB
import com.dm.dll.proyectoindividual.data.network.repositories.UsersRepository


class GetUserByIdDBUserCase {
    suspend fun invoke(id: String): UserDB? {

        return UsersRepository().getUserByID(id).getOrNull()

    }
}