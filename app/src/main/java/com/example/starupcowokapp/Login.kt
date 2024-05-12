package com.example.starupcowokapp

import Fragments.Space
import Models.user
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.starupcowokapp.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import utils.createnotification
import java.util.UUID

class Login : AppCompatActivity() {
    val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        binding.signup.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, Signup::class.java))

        })
        binding.loginBtn.setOnClickListener(View.OnClickListener {
            if (binding.email.text.toString().equals("") or binding.password.text.toString()
                    .equals("")
            ) {
                Toast.makeText(this@Login, "Please fill the email and password", Toast.LENGTH_SHORT)
                    .show()
            } else {

                var user = user(binding.email.text.toString(), binding.password.text.toString())
                Firebase.auth.signInWithEmailAndPassword(user.Password!!, user.Email!!)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(this, Home::class.java))
                            FirebaseFirestore.getInstance().collection("user").document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                                var img=it.data!!["image"].toString()
                                createnotification("Log IN","log in to your account","Home()",Firebase.auth.currentUser!!.uid,"",img)
                            }

                            finish()
                        } else {
                            Toast.makeText(
                                this@Login,
                                it.exception?.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }

        })
        binding.forgot.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,forgotpassword::class.java))
        })
    }
}
