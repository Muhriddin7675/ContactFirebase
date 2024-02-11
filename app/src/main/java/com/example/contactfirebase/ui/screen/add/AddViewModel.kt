package com.example.contactfirebase.ui.screen.add

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AddViewModel {
    val progressLiveData: StateFlow<Boolean>
    val errorMessageLiveData : Flow<String>

    fun closeScreen()
    fun addContact(firstName: String, lastName: String, phone:String)
}