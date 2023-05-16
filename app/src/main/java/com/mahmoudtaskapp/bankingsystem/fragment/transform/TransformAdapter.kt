package com.mahmoudtaskapp.bankingsystem.fragment.transform

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudtaskapp.bankingsystem.databinding.TransformLayoutBinding
import com.mahmoudtaskapp.bankingsystem.module.Transform
import androidx.recyclerview.widget.ListAdapter

class TransformAdapter : ListAdapter<Transform, TransformAdapter.TransformViewHolder>(DiffCallback)  {


    class TransformViewHolder(private val binding : TransformLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(item:Transform){
                binding.dataTransform = item
                binding.executePendingBindings()
            }

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransformViewHolder {
        return TransformViewHolder(
            TransformLayoutBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: TransformViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)

    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Transform>() {
            override fun areItemsTheSame(oldItem: Transform, newItem: Transform): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Transform, newItem: Transform): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}