package com.example.starupcowokapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.starupcowokapp.databinding.ActivityProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso


class ProfileActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    override fun onStart() {
        super.onStart()

        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("user")
        usersCollection.document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener { querySnapshot ->

                if (querySnapshot != null) {
                    binding.name.text = querySnapshot.data!!["name"].toString()
                    binding.email.text = querySnapshot.data!!["email"].toString()
                    binding.username.text = querySnapshot.data!!["name"].toString()
                    binding.age.text=querySnapshot.data!!["age"].toString()
                    binding.gender.text=querySnapshot.data!!["gender"].toString()
                    binding.phoneNumber.text=querySnapshot.data!!["phone_number"].toString()
                    if(querySnapshot.data!!["image"]!=null){
                        Picasso.get().load(querySnapshot.data!!["image"].toString()).into(binding.image)
                    }
                }
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }

}