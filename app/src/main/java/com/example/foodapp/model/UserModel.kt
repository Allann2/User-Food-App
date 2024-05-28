package com.example.foodapp.model

data class UserModel(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val role: String? = "user" // Add role attribute
)