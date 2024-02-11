package com.example.contactfirebase.ui.screen.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactfirebase.data.model.ContactUIDate
import com.example.contactfirebase.domain.AppRepositoryImpl
import com.example.contactfirebase.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModelImpl @Inject constructor(
    private val navigator: AppNavigator,
    private val repositoryImpl: AppRepositoryImpl
) : ViewModel(), ContactsViewModel {

    override var openBottomSheetDialog: ((ContactUIDate) -> Unit)? = null
    override val progressStateFlow = MutableStateFlow<Boolean>(false)
    override val errorMessageFlow = MutableSharedFlow<String>()
    override val contactFlow: MutableSharedFlow<List<ContactUIDate>> = repositoryImpl.contactDataFlow
    override val errorMessage: MutableSharedFlow<String> = repositoryImpl.errorMessage
    override val successFlow =
        MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    override fun openAddScreen() {
        viewModelScope.launch {
            navigator.navigationTo(ContactScreenDirections.actionContactScreenToAddScreen())
        }
    }

    override fun openEditScreen(data: ContactUIDate) {
        viewModelScope.launch {
            navigator.navigationTo(
                ContactScreenDirections.actionContactScreenToEditScreen(
                    data.dacId,
                    data.firstName,
                    data.lastName,
                    data.phone
                )
            )
        }
    }

    override fun openBottomSheetDialog(data: ContactUIDate) {
        openBottomSheetDialog?.invoke(data)
    }

    override fun deleteClick(data: ContactUIDate) {
        progressStateFlow.tryEmit(true)
        repositoryImpl.deleteContact(data)
            .onEach {
                it.onSuccess {
                    progressStateFlow.tryEmit(false)
                    errorMessage.emit("Delete")
                }
                it.onFailure {
                    progressStateFlow.tryEmit(false)
                    errorMessage.emit(it.message.toString())
                }
            }
            .launchIn(viewModelScope)

    }
}

//    val contactFlow = MutableSharedFlow<List<ContactUIData>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
//    val errorMessage = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

//    fun getAllContact() {
//        repository.getAllContact()
//            .onEach {
//                it.onSuccess { list ->
//                    contactFlow.emit(list)
//                }
//                it.onFailure { throwable ->
//                    throwable.message?.let { it1 -> errorMessage.emit(it1) }
//                }
//            }
//            .catch {  }
//            .launchIn(viewModelScope)
//    }





