package com.example.starupcowokapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.starupcowokapp.databinding.ActivityAccountDetailBinding
import com.example.starupcowokapp.databinding.ActivityForgotpasswordBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class forgotpassword : AppCompatActivity() {
    val binding by lazy {
        ActivityForgotpasswordBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.resepassword.setOnClickListener(View.OnClickListener {
            var email=binding.email.text.toString()
            if (email.equals("")){
                Toast.makeText(this,"Email is empty",Toast.LENGTH_SHORT).show()
            }
            else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnSuccessListener {
                    Toast.makeText(this,"Reset password link has been send to the regester email id",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,Login::class.java))
                }.addOnFailureListener {
                    Toast.makeText(this,"Reset password link has not been send due to some error",Toast.LENGTH_SHORT).show()
                }
            }
        })
        binding.back.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
    }
}