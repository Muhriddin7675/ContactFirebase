package com.example.contactfirebase.domain

import com.example.contactfirebase.data.model.AddContact
import com.example.contactfirebase.data.model.ContactUIDate
import kotlinx.coroutines.flow.Flow


interface AppRepository {
fun addContact(data:AddContact):Flow<Result<Unit>>
fun editContact(data:ContactUIDate):Flow<Result<Unit>>
fun deleteContact(data: ContactUIDate):Flow<Result<Unit>>
}