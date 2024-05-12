package com.example.starupcowokapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import com.example.starupcowokapp.databinding.ActivityEventdetailBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class Eventdetail : AppCompatActivity() {
    val binding by lazy {
        ActivityEventdetailBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val id = intent.getStringExtra("id")
        FirebaseFirestore.getInstance().collection("Event").document(id.toString()).get().addOnSuccessListener {
            if(it!=null){
                if (it.data!!["eventurl"].toString()!="") {
                    Picasso.get().load(it.data!!["eventurl"].toString()).placeholder(
                        R.drawable.loading
                    ).into(binding.imges)
                }else{
                    binding.imges.visibility=View.GONE
                }
                binding.Title.setText(it.data!!["eventtitle"].toString())
                binding.dateAndTime.setText(it.data!!["eventdate"].toString())
                binding.discription.setText(it.data!!["eventdiscription"].toString())
                binding.Location.setText(it.data!!["eventlocation"].toString())
            }
        }


    }
}