package com.example.contactfirebase.ui.screen.edit

import com.example.contactfirebase.data.model.ContactUIDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface EditViewModel {
    val progressStateFlow :StateFlow<Boolean>
    val errorMessageFlow :Flow<String>

    fun closeScreen()
    fun editContact(data:ContactUIDate)
}