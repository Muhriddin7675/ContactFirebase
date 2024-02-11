package com.example.contactfirebase.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.contactfirebase.data.model.ContactUIDate
import com.example.contactfirebase.databinding.ItemContactBinding

class ContactAdapter :
    ListAdapter<ContactUIDate, ContactAdapter.ContactViewHolder>(ContactDiffUtil) {

    private lateinit var itemLongClick: (ContactUIDate) -> Unit

    object ContactDiffUtil : DiffUtil.ItemCallback<ContactUIDate>() {
        override fun areItemsTheSame(oldItem: ContactUIDate, newItem: ContactUIDate): Boolean =
            oldItem.dacId == newItem.dacId

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ContactUIDate, newItem: ContactUIDate): Boolean =
            oldItem == newItem
    }

    inner class ContactViewHolder(private val binding: ItemContactBinding) :
        ViewHolder(binding.root) {
        init {
            binding.root.setOnLongClickListener {
                itemLongClick.invoke(getItem(adapterPosition))
                return@setOnLongClickListener true
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind() {
            getItem(adapterPosition).apply {
                binding.textName.text = "$firstName $lastName"
                binding.textNumber.text = phone
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
        ContactViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) = holder.bind()

    fun itemLongClickListener(block :(ContactUIDate) ->Unit){
        itemLongClick = block
    }
}