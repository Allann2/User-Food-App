package com.example.foodapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapter.MenuAdapter
import com.example.foodapp.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private val originalFoodName = listOf ("Burger", "Sandwich", "Pizza", "Fries")
    private val originalMenuItemPrice = listOf("$100", "$75", "$50", "$25")
    private val originalImage = listOf(
        R.drawable.burbur,
        R.drawable.sandwic,
        R.drawable.pizza,
        R.drawable.fries
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }
    private val filteredMenuFoodName = mutableListOf<String>()
    private val filteredMenuItemPrice = mutableListOf<String>()
    private val filteredMenuImage = mutableListOf<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
//        adapter = MenuAdapter(
//            filteredMenuFoodName,
//            filteredMenuItemPrice,
//            filteredMenuImage,
//            requireContext()
//        )
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter

        //setup for search view
        setupSearchView()
        //show all menuItems
        showAllMenu()


        return binding.root
    }

    private fun showAllMenu() {
        filteredMenuFoodName.clear()
        filteredMenuItemPrice.clear()
        filteredMenuImage.clear()

        filteredMenuFoodName.addAll(originalFoodName)
        filteredMenuItemPrice.addAll(originalMenuItemPrice)
        filteredMenuImage.addAll(originalImage)

        adapter.notifyDataSetChanged()
    }

    private fun setupSearchView() {
        binding.searchView2.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }
        })
    }
            private fun filterMenuItems(query: String) {
                filteredMenuFoodName.clear()
                filteredMenuItemPrice.clear()
                filteredMenuImage.clear()

                originalFoodName.forEachIndexed { index, foodName ->
                    if (foodName.contains(query, ignoreCase = true)) {
                        filteredMenuFoodName.add(foodName)
                        filteredMenuItemPrice.add(originalMenuItemPrice[index])
                        filteredMenuImage.add(originalImage[index])
                    }
                }
                adapter.notifyDataSetChanged()
            }
        }