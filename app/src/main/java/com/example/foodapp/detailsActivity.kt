package com.example.foodapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ActivityDetailsBinding
import com.example.foodapp.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class detailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailsBinding
    private var foodName: String? = null
    private var foodImage: String? = null
    private var foodDescription: String? = null
    private var foodPrice: String? = null
    private lateinit var auth : FirebaseAuth
            
            
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //init firebaseautg
        auth = FirebaseAuth.getInstance()
        foodName = intent. getStringExtra("MenuItemName")
        foodDescription = intent. getStringExtra("MenuItemDescription")
        foodPrice = intent. getStringExtra("MenuItemPrice")

        foodImage = intent.getStringExtra("MenuItemImage")

        with(binding){
            detailFoodName.text = foodName
            textView21.text = foodDescription
            Glide.with(this@detailsActivity).load(Uri.parse(foodImage)).into(detailFoodImage)

        }

        binding.returnButton.setOnClickListener {
            finish()
        }

        
        binding.button3.setOnClickListener{
            addItemToCart()
        }
    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid?:""

        // create cart items object
        val cartItem = CartItems(foodName.toString(), foodPrice.toString(), foodDescription.toString(), foodImage.toString(), 1)

        //save data from cart item to database
        database.child("user").child(userId).child("CartItems").push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this,"Items added into cart Successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Item not Added!", Toast.LENGTH_SHORT).show()
        }
    }
}