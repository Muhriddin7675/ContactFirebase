package com.example.contactfirebase.ui.screen.contact

import com.example.contactfirebase.data.model.ContactUIDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ContactsViewModel {
    var openBottomSheetDialog: ((ContactUIDate) -> Unit)?
    val progressStateFlow: StateFlow<Boolean>
    val errorMessageFlow: Flow<String>
    val successFlow :Flow<String>
    val contactFlow:Flow<List<ContactUIDate>>
    val errorMessage:Flow<String>


    fun openAddScreen()
    fun openEditScreen(data: ContactUIDate)
    fun openBottomSheetDialog(data: ContactUIDate)
    fun deleteClick(data: ContactUIDate)
}