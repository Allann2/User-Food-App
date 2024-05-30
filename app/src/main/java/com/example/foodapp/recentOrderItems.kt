package com.example.foodapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.adapter.RecentBuyAdapter
import com.example.foodapp.databinding.ActivityRecentOrderItemsBinding
import com.example.foodapp.model.OrderDetails

class recentOrderItems : AppCompatActivity() {

    private val binding: ActivityRecentOrderItemsBinding by lazy {
        ActivityRecentOrderItemsBinding.inflate(layoutInflater)
    }
    private lateinit var allFoodNames: ArrayList<String>
    private lateinit var allFoodPrices: ArrayList<String>
    private lateinit var allFoodImages: ArrayList<String>
    private lateinit var allFoodQuantities: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val recentOrderDetails =
            intent.getSerializableExtra("RecentBuyOrderItem") as ArrayList<OrderDetails>
        recentOrderDetails.let { orderDetails ->
            if (orderDetails.isNotEmpty()) {
                val recentOrderDetail = orderDetails[0]

                allFoodNames = recentOrderDetail.foodNames as ArrayList<String>
                allFoodPrices = recentOrderDetail.foodPrices as ArrayList<String>
                allFoodImages = recentOrderDetail.foodImages as ArrayList<String>
                allFoodQuantities = recentOrderDetail.foodQuantities as ArrayList<Int>
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        val recyclerView = binding.recyclerViewRecent
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecentBuyAdapter(this, allFoodNames, allFoodImages, allFoodPrices, allFoodQuantities)
        recyclerView.adapter = adapter
    }
}
