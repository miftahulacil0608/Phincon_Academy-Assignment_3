package com.example.mytraver.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytraver.R
import com.example.mytraver.databinding.ItemItineraryLayoutBinding
import com.example.mytraver.domain.model.ItineraryData
import com.example.mytraver.presentation.adapter.listener.OnItemVerticalAdapterItinerary

class RecyclerViewItineraryAdapter(
    private var listItem: List<ItineraryData> = emptyList(),
    private val listener: OnItemVerticalAdapterItinerary
) : RecyclerView.Adapter<RecyclerViewItineraryAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ItemItineraryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItineraryData) {
            binding.apply {
                tvTitleItinerary.text = item.name
                tvNotes.text = root.resources.getString(R.string.label_notes, item.notes)
                tvLocation.text = item.location
                tvCalendar.text = root.resources.getString(R.string.label_duration, item.duration)
                root.setOnClickListener {
                    listener.onClick(item)
                }
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerViewItineraryAdapter.MyViewHolder, position: Int) {
        val item = listItem[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemItineraryLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listItem.size


    fun addNewData(newListData: List<ItineraryData>) {
        listItem = newListData
        notifyDataSetChanged()
    }
}