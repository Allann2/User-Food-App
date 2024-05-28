package com.example.foodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.foodapp.databinding.ActivitySignupBinding
import com.example.foodapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SigninActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var username: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val binding:ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(binding.root)
            //initialize Firebase auth
            auth = Firebase.auth

            //initialize Firebase DB
            database = Firebase.database.reference

            binding.signupButton.setOnClickListener{
                username = binding.signupName.text.toString().trim()
                email = binding.signupEmail.text.toString().trim()
                password = binding.signupPassword.text.toString().trim()

                if (email.isBlank()||password.isBlank()||username.isBlank()){
                    Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_LONG).show()
                }else{
                    createAccount(email,password)
                }
            }
            binding.alreadyhave.setOnClickListener{
                val intent = Intent(this, StartActivity::class.java)
                startActivity(intent)
            }


        }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            task ->
            if(task.isSuccessful){
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_LONG).show()
                saveUserData()
                startActivity(Intent(this, StartActivity::class.java))
//                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_LONG).show()
                Log.d("Account", "createAccount: Failure",task.exception)
            }
        }
    }

//    private fun saveUserData() {
//        //retrieve data from input field
//        username = binding.signupName.text.toString()
//        email = binding.signupEmail.text.toString().trim()
//        password = binding.signupPassword.text.toString().trim()
//
//        val user = UserModel(username,email,password)
//        val userId = FirebaseAuth.getInstance().currentUser!!.uid
//        //save data to Firebase Database
//        database.child("user").child(userId).setValue(user)
//    }
private fun saveUserData() {
    email = binding.signupEmail.text.toString().trim()
    password = binding.signupPassword.text.toString().trim()
    val userId = auth.currentUser?.uid
    val user = UserModel(
        name = binding.signupName.text.toString().trim(),
        email = email,
        password = password,
//        phone = binding.signupPhone.text.toString().trim(),
//        address = binding.signupAddress.text.toString().trim(),
        role = "user" // Set role as "user"
    )

    userId?.let {
        database.child("user").child(it).setValue(user)
            .addOnSuccessListener {
                Log.d("Database", "User data saved successfully")
            }
            .addOnFailureListener { e ->
                Log.d("Database", "Error saving user data: ${e.message}")
            }
    }
}
}