package com.example.starupcowokapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.starupcowokapp.databinding.ActivityAccountDetailBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class Account_detail : AppCompatActivity() {
    val binding by lazy {
        ActivityAccountDetailBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("user")
        usersCollection.document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener { querySnapshot ->

                if (querySnapshot != null) {
                    binding.name.text = querySnapshot.data!!["name"].toString()
                    binding.email.text = querySnapshot.data!!["email"].toString()
                    binding.gender.text=querySnapshot.data!!["gender"].toString()
                    binding.phoneNumber.text=querySnapshot.data!!["phone_number"].toString()
                    binding.dob.text=querySnapshot.data!!["date_of_birth"].toString()
                    binding.userid.text=Firebase.auth.currentUser!!.uid
                    if(querySnapshot.data!!["image"]!=null){
                        Picasso.get().load(querySnapshot.data!!["image"].toString()).placeholder(R.drawable.profile).into(binding.image)
                    }
                }
            }
        binding.editprofile.setOnClickListener(View.OnClickListener {
            val intent=Intent(this,Signup::class.java)
            intent.putExtra("message","Editprofile")
            startActivity(intent)
        })
    }
    }
