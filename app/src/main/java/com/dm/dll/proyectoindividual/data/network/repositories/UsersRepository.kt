package com.dm.dll.proyectoindividual.data.network.repositories;

import com.dm.dll.proyectoindividual.data.network.entities.UserDB
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UsersRepository {

    private val db = Firebase.firestore

    suspend fun saveUserDB(id: String, email: String, name: String): Result<UserDB> = runCatching {
        val us = UserDB(id, email, name)
        db.collection("Users").add(us).await() //el json se va guardar en una coleccion
        //el await es para que espera a que termine

        return@runCatching us
    }

    suspend fun getUserByID(id: String) = runCatching {
        val us = UserDB(id, "", "")

        val v = db.collection("Users")
            .document(us.id)
            .get()
            .await<DocumentSnapshot?>()
            ?.toObject<UserDB>(UserDB::class.java) //se encarga de transofrmar a UserDB

        return@runCatching v

    }//.getOrNull()

    suspend fun getUserByName(name: String) = runCatching {
        val querySnapshot = db.collection("Users")
            .whereEqualTo("name", name)
            .get()
            .await()

        val users = querySnapshot.documents.mapNotNull { it.toObject<UserDB>(UserDB::class.java) }

        return@runCatching users
    }


    suspend fun updateUserByID(id: String) = runCatching {
        val us = UserDB(id, "", "")

        val v = db.collection("Users")
            .document(us.id)
            .get()
            .await<DocumentSnapshot?>()
            ?.toObject<UserDB>(UserDB::class.java) //se encarga de transofrmar a UserDB

        //PARA ACTUALIZAR PISTA:
        if (v != null) {
            v!!.email = "dddd"
            db.collection("Users").document(v!!.id).set(v)

        }


    }

    suspend fun deleteUserFromDB(id: String): Result<Void?> = runCatching {
        val usFirebase = db.collection("Users").document(id).delete().await()
        return@runCatching usFirebase
    }

    suspend fun deleteUserByNameFromDB(name: String): Result<Unit?> = runCatching {
        val querySnapshot = db.collection("Users")
            .whereEqualTo("name", name)
            .get()
            .await()

        for (document in querySnapshot.documents) {
            document.reference.delete().await()
        }
    }


}