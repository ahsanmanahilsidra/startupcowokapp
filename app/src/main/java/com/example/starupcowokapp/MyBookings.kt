package com.example.starupcowokapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.starupcowokapp.databinding.ActivityMyAttandenceBinding
import com.example.starupcowokapp.databinding.ActivityMyBookingsBinding

class MyBookings : AppCompatActivity() {
    val binding by lazy {
        ActivityMyBookingsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        // Enable the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title=""
        // Set toolbar navigation click listener
        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }
}