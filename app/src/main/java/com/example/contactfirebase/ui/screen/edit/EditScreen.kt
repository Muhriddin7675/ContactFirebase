package com.example.contactfirebase.ui.screen.edit

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.contactfirebase.R
import com.example.contactfirebase.databinding.ScreenContactEditBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.navArgs
import com.example.contactfirebase.data.model.ContactUIDate
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges

@AndroidEntryPoint
class EditScreen : Fragment(R.layout.screen_contact_edit) {
    private val binding by viewBinding(ScreenContactEditBinding::bind)
    private val viewModel: EditViewModel by viewModels<EditViewModelImpl>()
    private val navArgs:EditScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.editInputFirstName.setText(navArgs.firstName)
        binding.editInputLastName.setText(navArgs.lastName)
        binding.editInputPhone.setText(navArgs.phone)

        combine(
            binding.editInputFirstName.textChanges().map { it.length > 3 },
            binding.editInputLastName.textChanges().map { it.length > 3 },
            binding.editInputPhone.textChanges().map { it.length == 13 && it.startsWith("+998") },
            transform = { firstName, lastName, phone -> firstName && lastName && phone }
        ).onEach {
            binding.buttonEdit.isEnabled = it }.flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)

        binding.buttonBack.setOnClickListener {
            viewModel.closeScreen()
        }

        binding.buttonEdit.setOnClickListener {
            viewModel.editContact(
                ContactUIDate(
                    navArgs.docId,
                    binding.editInputFirstName.text.toString(),
                    binding.editInputLastName.text.toString(),
                    binding.editInputPhone.text.toString())
            )
            viewModel.closeScreen()
        }

        viewModel.progressStateFlow.onEach {
            if (it) {
                binding.buttonEdit.isVisible = false
                binding.frameLoading.isVisible = true
                binding.progress.show()
            } else {
                binding.buttonEdit.isVisible = true
                binding.frameLoading.isVisible = false
                binding.progress.hide()
            }
        }.launchIn(lifecycleScope)
    }
}