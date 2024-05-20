package com.example.starupcowokapp

import Adopters.BookingAdopter
import Models.Booking
import MybookingAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.starupcowokapp.databinding.ActivityMyAttandenceBinding
import com.example.starupcowokapp.databinding.ActivityMyBookingsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

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

            var bookingList=ArrayList<Booking>()
            var adapter= MybookingAdapter(this,bookingList)
            binding.reciclerview.layoutManager=
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            binding.reciclerview.adapter=adapter
            Firebase.firestore.collection("Booking").whereEqualTo("userid",Firebase.auth.currentUser!!.uid).get().addOnSuccessListener(){
                var templist= arrayListOf<Booking>()
                for(i in it.documents){
                    var booking: Booking = i.toObject<Booking>()!!
                    templist.add(booking)
                }
                templist.reverse()
                bookingList.addAll(templist)
                adapter.notifyDataSetChanged();
            }




    }
}