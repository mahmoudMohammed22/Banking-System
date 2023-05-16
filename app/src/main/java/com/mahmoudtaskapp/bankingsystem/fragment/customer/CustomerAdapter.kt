package com.mahmoudtaskapp.bankingsystem.fragment.customer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudtaskapp.bankingsystem.databinding.CustomerLayoutBinding
import com.mahmoudtaskapp.bankingsystem.module.Customer

class CustomerAdapter(private val onItemClicked: (Customer) -> Unit)
    : ListAdapter<Customer, CustomerAdapter.CustomerViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        return CustomerViewHolder(
            CustomerLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),parent , false
            )
        )
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class CustomerViewHolder(private var binding: CustomerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Customer) {
            binding.dataCutomer = item
            binding.executePendingBindings()
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Customer>() {
            override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}