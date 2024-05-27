package com.example.foodapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.PopularItemBinding
import com.example.foodapp.detailsActivity


class PopularAdapter (private val items:List<String>,
                      private val price: List<String>,
                      private val image:List<Int>,
                      private val requireContext:Context )
    : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item = items[position]
            val images = image[position]
            val prices = price[position]
            holder.bind(item,prices,images)

        holder.itemView.setOnClickListener{
            val intent = Intent( requireContext, detailsActivity::class.java)
            intent.putExtra("MenuItemName", item)
            intent.putExtra("MenuItemImage", images)
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    class PopularViewHolder (private val binding:PopularItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val imagesView = binding.menuImage
        fun bind(item: String, prices: String, images: Int) {
            binding.popularName.text = item
            binding.menuPrice.text = prices
            imagesView.setImageResource(images)
        }

    }
}