package com.example.starupcowokapp

import Models.Space
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.starupcowokapp.databinding.ActivityBookingcompletedBinding

class bookingcompleted : AppCompatActivity() {
    val binding by lazy {
        ActivityBookingcompletedBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.morespaces.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,Home()::class.java))
        })
    }
}