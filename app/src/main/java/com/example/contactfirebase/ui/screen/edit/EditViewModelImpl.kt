package com.example.contactfirebase.ui.screen.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactfirebase.data.model.AddContact
import com.example.contactfirebase.data.model.ContactUIDate
import com.example.contactfirebase.domain.AppRepositoryImpl
import com.example.contactfirebase.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModelImpl @Inject constructor(
    private val repositoryImpl: AppRepositoryImpl,
    private val navigator: AppNavigator
) : ViewModel(), EditViewModel {
    override val progressStateFlow = MutableStateFlow<Boolean>(false)
    override val errorMessageFlow = MutableSharedFlow<String>()

    override fun closeScreen() {
        viewModelScope.launch {
            navigator.popBack()
        }
    }

    override fun editContact(data: ContactUIDate) {
        repositoryImpl.editContact(data)
            .onEach {
                it.onSuccess {
                    errorMessageFlow.emit("Success")
                }
                it.onFailure {
                    errorMessageFlow.emit(it.message.toString())
                }
            }
            .launchIn(viewModelScope)
    }
}