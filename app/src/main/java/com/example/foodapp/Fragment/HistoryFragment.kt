package com.example.foodapp.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.adapter.BuyAgainAdapter
import com.example.foodapp.databinding.FragmentHistoryBinding
import com.example.foodapp.model.OrderDetails
import com.example.foodapp.recentOrderItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private var listOfOrderedItem: MutableList<OrderDetails> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        //inflate the layout for this fragment

        //init firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        // Retrieve and display the user order history
        retrieveBuyHistory()

        //recent buy button click
        binding.recentBuyItem.setOnClickListener {
            seeItemsRecentBuy()
        }
        //setup RecyclerView
        return binding.root
    }

    private fun seeItemsRecentBuy() {
        listOfOrderedItem.firstOrNull()?.let { recentBuy ->
            val intent = Intent(requireContext(), recentOrderItems::class.java)
            intent.putExtra("RecentBuyOrderItem", recentBuy)
            startActivity(intent)
        }
    }

    //retrieve buy history
    private fun retrieveBuyHistory() {
        binding.recentBuyItem.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid ?: ""
        val buyItemReference: DatabaseReference =
            database.reference.child("user").child(userId).child("BuyHistory")
        val sortingQuery = buyItemReference.orderByChild("currentTime")

        sortingQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children) {
                    val buyHistoryItem = buySnapshot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let {
                        listOfOrderedItem.add(it)
                    }
                }
                listOfOrderedItem.reverse()
                if (listOfOrderedItem.isNotEmpty()) {
                    setDataInRecentBuyItem()
                    setPreviousBuyRecyclerView()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        })
    }

    private fun setDataInRecentBuyItem() {
        binding.recentBuyItem.visibility = View.VISIBLE
        val recentOrderItem = listOfOrderedItem.firstOrNull()
        recentOrderItem?.let {
            with(binding) {
                buyAgainFoodName.text = it.foodNames?.firstOrNull() ?: ""
                buyAgainFoodPrice.text = it.foodPrices?.firstOrNull() ?: ""
                val image = it.foodImages?.firstOrNull() ?: ""
                val uri = Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(buyAgainFoodImage)

                listOfOrderedItem.reverse()
                if (listOfOrderedItem.isNotEmpty()) {
                    // Additional logic if needed
                }
            }
        }
    }

    private fun setPreviousBuyRecyclerView() {
        val buyAgainFoodNamer = mutableListOf<String>()
        val buyAgainFoodPrice = mutableListOf<String>()
        val buyAgainFoodImage = mutableListOf<String>()
        for (i in 1 until listOfOrderedItem.size) {
            listOfOrderedItem[i].foodNames?.firstOrNull()?.let {
                buyAgainFoodNamer.add(it)
                listOfOrderedItem[i].foodPrices?.firstOrNull()?.let {
                    buyAgainFoodPrice.add(it)
                    listOfOrderedItem[i].foodImages?.firstOrNull()?.let {
                        buyAgainFoodImage.add(it)
                    }
                }
                val rv = binding.buyAgainRecyclerView
                rv.layoutManager = LinearLayoutManager(requireContext())
                buyAgainAdapter = BuyAgainAdapter(
                    buyAgainFoodNamer,
                    buyAgainFoodPrice,
                    buyAgainFoodImage,
                    requireContext())
                rv.adapter = buyAgainAdapter
            }
        }
    }
}
