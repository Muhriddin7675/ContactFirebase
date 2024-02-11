package com.example.contactfirebase.ui.screen.contact

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.contactfirebase.R
import com.example.contactfirebase.databinding.ScreenContactBinding
import com.example.contactfirebase.ui.adapter.ContactAdapter
import com.example.contactfirebase.ui.dialog.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ContactScreen : Fragment(R.layout.screen_contact) {
    private val binding by viewBinding(ScreenContactBinding::bind)
    private val viewModel: ContactsViewModel by viewModels<ContactViewModelImpl>()
    private val adapter by lazy { ContactAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvContact.adapter = adapter
        binding.rvContact.layoutManager = LinearLayoutManager(requireContext())

        binding.btnAdd.setOnClickListener{
            viewModel.openAddScreen()
        }
        adapter.itemLongClickListener {
            viewModel.openBottomSheetDialog(it)
        }


        viewModel.openBottomSheetDialog = {
            val dialog = BottomSheetDialog()
            dialog.show(parentFragmentManager, "dilog")
            dialog.setOnClickDelete {
                viewModel.deleteClick(it)
            }
            dialog.setOnClickEdit {
                viewModel.openEditScreen(it)
            }
        }

        viewModel.contactFlow
            .onEach { adapter.submitList(it) }
            .launchIn(lifecycleScope)

        viewModel.errorMessage
            .onEach { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
            .launchIn(lifecycleScope)

        viewModel.errorMessageFlow
            .onEach { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
            .launchIn(lifecycleScope)

        viewModel.progressStateFlow
            .onEach {
//                binding.refreshLayout.isRefreshing = it
            }
            .launchIn(lifecycleScope)

    }

}