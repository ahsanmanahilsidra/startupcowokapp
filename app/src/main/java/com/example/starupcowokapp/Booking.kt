package com.example.starupcowokapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import com.example.starupcowokapp.databinding.ActivityBookingBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class Booking : AppCompatActivity() {
    val binding by lazy {
        ActivityBookingBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val id = intent.getStringExtra("id")
        val bookingid = intent.getStringExtra("bookingid")
        if (id!=null) {
            FirebaseFirestore.getInstance().collection("space").document(id.toString()).get()
                .addOnSuccessListener {
                    if (it != null) {
                        Picasso.get().load(it.data!!["spaceurl"].toString()).placeholder(
                            R.drawable.loading
                        ).into(binding.imges)
                        binding.Title.setText(it.data!!["spacetitle"].toString())
                        binding.price.setText(it.data!!["price"].toString())
                        binding.discription.setText(it.data!!["about"].toString())

                    }
                }
            binding.book.setOnClickListener(View.OnClickListener {
                val intent: Intent = Intent(this, paymentActivity::class.java)
                intent.putExtra("spaceid", id.toString())
                startActivity(intent)
            })
        }
        var spaceid:String
        if (bookingid!=null){
            binding.book.visibility=View.INVISIBLE
            FirebaseFirestore.getInstance().collection("Booking").document(bookingid.toString()).get()
                .addOnSuccessListener {
           var spaceid=it.data!!["spaceid"].toString()
                 binding.dateAndTime.setText("from${it.data!!["bookingdate"]} to ${it.data!!["bookingexpire"]} ")
                    FirebaseFirestore.getInstance().collection("space").document(spaceid).get()
                        .addOnSuccessListener {
                            if (it != null) {
                                Picasso.get().load(it.data!!["spaceurl"].toString()).placeholder(
                                    R.drawable.loading
                                ).into(binding.imges)
                                binding.Title.setText(it.data!!["spacetitle"].toString())
                                binding.price.setText(it.data!!["price"].toString())
                                binding.discription.setText(it.data!!["about"].toString())

                            }
                        }
                    }
                }

        }
    }