package com.example.mytraver.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mytraver.databinding.ItemTypeReferencesBinding
import com.example.mytraver.domain.model.TypeReferences
import com.example.mytraver.presentation.adapter.listener.OnItemTypeReferencesListener

class RecyclerViewTypeReferencesAdapter(
    private val listType: List<TypeReferences>,
    private val listener: OnItemTypeReferencesListener
) : RecyclerView.Adapter<RecyclerViewTypeReferencesAdapter.MyViewHolder>() {
    private var selectedPosition = -1

    inner class MyViewHolder(private val binding: ItemTypeReferencesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TypeReferences) {
            binding.ivType.setImageResource(item.image)
            binding.tvType.text = item.type
            val isSelected = bindingAdapterPosition == selectedPosition
            binding.root.isSelected = isSelected

            binding.ivClickable.isVisible = isSelected

            binding.root.setOnClickListener {
                val previousSelected = selectedPosition
                selectedPosition = bindingAdapterPosition

                listener.onItemClick(item.type)

                notifyItemChanged(previousSelected)
                notifyItemChanged(selectedPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewTypeReferencesAdapter.MyViewHolder {
        val binding =
            ItemTypeReferencesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listType.size

    override fun onBindViewHolder(
        holder: RecyclerViewTypeReferencesAdapter.MyViewHolder,
        position: Int
    ) {
        val item = listType[position]
        holder.bind(item)
    }
}