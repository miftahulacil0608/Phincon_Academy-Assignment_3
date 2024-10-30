package com.example.mytraver.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytraver.R
import com.example.mytraver.databinding.ItemVerticalLayoutBinding
import com.example.mytraver.databinding.RecommendedPlaceVerticalLayoutBinding
import com.example.mytraver.domain.model.DataItem
import com.example.mytraver.presentation.adapter.listener.OnItemVerticalAdapter

//TODO cuma bisa dipakai 3 view dengan data yg sama
class RecyclerViewVerticalAdapter(private var listItem: List<DataItem> = emptyList(), private val listener: OnItemVerticalAdapter, private val isRecommended:Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private const val VIEW_POPULAR = 1
        private const val VIEW_RECOMMENDED_FRAGMENT = 2
    }

    inner class MyViewHolderPopular(private val binding: ItemVerticalLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem) {
            binding.apply {
                Glide.with(binding.root)
                    .load(item.image)
                    .into(ivDestination)
                tvDescription.text = item.description
                tvDestination.text = item.name
                tvLocation.text = item.location
                tvRating.text = item.popularity
                ratingBar.rating = item.popularity.toFloat()
                root.setOnClickListener {
                    listener.onClickVerticalItem(item)
                }
            }
        }
    }

    inner class MyViewHolderRecommendedFragment(private val binding: RecommendedPlaceVerticalLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem) {
            binding.apply {
                Glide.with(binding.root)
                    .load(item.image)
                    .into(bgDestination)
                tvDestination.text = item.name
                tvLocation.text = item.location
                tvRating.text = item.popularity
                tvDuration.text = root.resources.getString(R.string.label_duration, item.duration)
                ratingBar.rating = item.popularity.toFloat()
                root.setOnClickListener {
                    listener.onClickVerticalItem(item)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isRecommended) VIEW_RECOMMENDED_FRAGMENT else VIEW_POPULAR

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_RECOMMENDED_FRAGMENT){
            val binding = RecommendedPlaceVerticalLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            MyViewHolderRecommendedFragment(binding)
        }else{
            val binding = ItemVerticalLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            MyViewHolderPopular(binding)
        }
    }

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listItem[position]
        when(holder){
            is MyViewHolderRecommendedFragment -> holder.bind(item)
            is MyViewHolderPopular -> holder.bind(item)
        }
    }

    fun addNewData(newListData: List<DataItem>) {
        listItem
        listItem = newListData
        notifyDataSetChanged()
    }

}