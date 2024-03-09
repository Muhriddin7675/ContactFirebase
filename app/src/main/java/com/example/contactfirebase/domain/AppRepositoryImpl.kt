package com.example.contactfirebase.domain

import com.example.contactfirebase.data.model.AddContact
import com.example.contactfirebase.data.model.ContactUIDate
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor() : AppRepository {

    private val fireStore = Firebase.firestore
    val contactDataFlow = MutableSharedFlow<List<ContactUIDate>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val errorMessage =
        MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    init {
        fireStore.collection("task")
            .addSnapshotListener { querySnapshot, error ->
                val data = ArrayList<ContactUIDate>()
                querySnapshot?.forEach {
                    data.add(
                        ContactUIDate(
                            dacId = it.id,
                            firstName = it.data.getOrDefault("firstName", "Default") as String,
                            lastName = it.data.getOrDefault("lastName", "Default") as String,
                            phone = it.data.getOrDefault("phone", "~") as String
                        )
                    )
                }
                contactDataFlow.tryEmit(data)

//                errorMessage.tryEmit(error?.message.toString())
            }
    }

    override fun addContact(data: AddContact): Flow<Result<Unit>> = callbackFlow {
        fireStore.collection("task")
            .document(System.currentTimeMillis().toString())
            .set(data)
            .addOnSuccessListener {
                trySend(Result.success(Unit))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun editContact(data: ContactUIDate): Flow<Result<Unit>> = callbackFlow {
        fireStore.collection("task")
            .document(data.dacId)
            .set(AddContact(data.firstName, data.lastName, data.phone))
            .addOnSuccessListener {
                trySend(Result.success(Unit))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun deleteContact(data: ContactUIDate): Flow<Result<Unit>> = callbackFlow {
        fireStore.collection("task")
            .document(data.dacId)
            .delete()
            .addOnSuccessListener {
                trySend(Result.success(Unit))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()

    }
}