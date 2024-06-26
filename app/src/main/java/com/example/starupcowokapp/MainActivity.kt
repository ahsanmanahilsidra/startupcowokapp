package com.example.starupcowokapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, ACTIVITY_ONBOARDING1::class.java))
            if (FirebaseAuth.getInstance().currentUser==null) {

            }else
            {
                startActivity(Intent(this, Home::class.java))
            }
            finish()
        },3000)
    }
}