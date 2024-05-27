package com.example.foodapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ActivityDetailsBinding

class detailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailsBinding
    private var foodName: String? = null
    private var foodImage: String? = null
    private var foodDescription: String? = null
    private var foodPrice: String? = null
            
            
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        foodName = intent. getStringExtra("MenuItemName")
        foodDescription = intent. getStringExtra("MenuItemDescription")
        foodPrice = intent. getStringExtra("MenuItemPrice")

        foodImage = intent.getStringExtra("MenuItemImage")

        with(binding){
            detailFoodName.text = foodName
            textView21.text = foodDescription
            Glide.with(this@detailsActivity).load(Uri.parse(foodImage)).into(detailFoodImage)

        }

        
        binding.button3.setOnClickListener{
            finish()
        }
    }
}