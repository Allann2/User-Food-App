package com.example.foodapp.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.foodapp.CongratsBottomSheet
import com.example.foodapp.PayOutActivity
import com.example.foodapp.R
import com.example.foodapp.adapter.CartAdapter
import com.example.foodapp.databinding.FragmentCartBinding


class CartFragment : Fragment() {

    private lateinit var binding:FragmentCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        val cartFoodName = listOf("Burger", "Sandwich", "Pizza", "Fries")
        val cartItemPrice = listOf("$100", "$75", "$50", "$25")
        val cartImage = listOf(
            R.drawable.burbur,
            R.drawable.sandwic,
            R.drawable.pizza,
            R.drawable.fries
        )
        val adapter = CartAdapter(ArrayList(cartFoodName), ArrayList(cartItemPrice), ArrayList(cartImage))
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter
        binding.proceedButton.setOnClickListener{
            val intent = Intent(requireContext(),PayOutActivity::class.java)
            startActivity(intent)
        }



        return binding.root
    }

    companion object {

            }
    }
