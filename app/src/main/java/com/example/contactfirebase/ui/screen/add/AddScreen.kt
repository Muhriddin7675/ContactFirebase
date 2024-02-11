package com.example.contactfirebase.ui.screen.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.contactfirebase.R
import com.example.contactfirebase.databinding.ScreenContactAddBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges

@AndroidEntryPoint
class AddScreen : Fragment(R.layout.screen_contact_add) {
    private val binding by viewBinding(ScreenContactAddBinding::bind)
    private val viewModel: AddViewModel by viewModels<AddViewModelImpl>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonBack.setOnClickListener {
            viewModel.closeScreen()
        }
        combine(
            binding.editInputFirstName.textChanges().map { it.length > 3 },
            binding.editInputLastName.textChanges().map { it.length > 3 },
            binding.editInputPhone.textChanges().map { it.length == 13 && it.startsWith("+998") },
            transform = { firstName, lastName, phone -> firstName && lastName && phone }
        ).onEach { binding.buttonAdd.isEnabled = it }.flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)

        binding.buttonAdd.setOnClickListener {
            viewModel.addContact(
                binding.editInputFirstName.text.toString(),
                binding.editInputLastName.text.toString(),
                binding.editInputPhone.text.toString()
            )
            viewModel.closeScreen()
        }

        viewModel.errorMessageLiveData.onEach {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)


        viewModel.progressLiveData.onEach {
            if (it) {
                binding.buttonAdd.isVisible = false
                binding.frameLoading.isVisible = true
                binding.progress.show()
            } else {
                binding.buttonAdd.isVisible = true
                binding.frameLoading.isVisible = false
                binding.progress.hide()
            }
        }.launchIn(lifecycleScope)

    }

}