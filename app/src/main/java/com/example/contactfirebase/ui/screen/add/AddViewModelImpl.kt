package com.example.contactfirebase.ui.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactfirebase.data.model.AddContact
import com.example.contactfirebase.domain.AppRepositoryImpl
import com.example.contactfirebase.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModelImpl @Inject constructor(
    private val repositoryImpl: AppRepositoryImpl,
    private val navigator: AppNavigator
) : ViewModel(), AddViewModel {
    override val progressLiveData = MutableStateFlow<Boolean>(false)
    override val errorMessageLiveData = MutableSharedFlow<String>()

    override fun closeScreen() {
        viewModelScope.launch {
            navigator.popBack()
        }
    }

    override fun addContact(firstName: String, lastName: String, phone: String) {
        progressLiveData.tryEmit(true)
     repositoryImpl.addContact(AddContact(firstName,lastName,phone))
         .onEach {
              it.onSuccess {
                  progressLiveData.emit(false)
                  errorMessageLiveData.emit("Success")
              }
             it.onFailure {
                 progressLiveData.emit(false)
                 errorMessageLiveData.emit(it.message.toString())
             }
         }
         .launchIn(viewModelScope)
    }

}