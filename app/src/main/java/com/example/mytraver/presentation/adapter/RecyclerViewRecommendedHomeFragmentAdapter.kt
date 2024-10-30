package com.example.mytraver.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytraver.R
import com.example.mytraver.databinding.RecommendedPlaceHorizontalLayoutBinding
import com.example.mytraver.domain.model.DataItem
import com.example.mytraver.presentation.adapter.listener.OnItemHorizontalAdapter

class RecyclerViewRecommendedHomeFragmentAdapter(
    private var listItem: List<DataItem> = emptyList(),
    private val listener: OnItemHorizontalAdapter,
) : RecyclerView.Adapter<RecyclerViewRecommendedHomeFragmentAdapter.MyViewHolder>() {


    inner class MyViewHolder(private val binding: RecommendedPlaceHorizontalLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem) {
            binding.apply {
                Glide.with(binding.root)
                    .load(item.image)
                    .into(bgDestination)
                tvDestination.text = item.name
                tvLocation.text = item.location
                tvRating.text = item.popularity
                ratingBar.rating = item.popularity.toFloat()
                tvDuration.text = root.resources.getString(R.string.label_duration, item.duration)
                root.setOnClickListener {
                    listener.onClickRecommendedItem(item)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecommendedPlaceHorizontalLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listItem[position]
        holder.bind(item)
    }

    override fun getItemCount() = listItem.size

    fun addNewData(newListData: List<DataItem>) {
        listItem = newListData
        notifyDataSetChanged()
    }

}