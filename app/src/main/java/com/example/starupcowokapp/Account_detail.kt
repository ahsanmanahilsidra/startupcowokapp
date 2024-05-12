package com.example.starupcowokapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.example.starupcowokapp.databinding.ActivityAccountDetailBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso

private lateinit var curretid: String
private var selectedItem: String? = null

class Account_detail : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    val binding by lazy {
        ActivityAccountDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.spinner.visibility = View.GONE
        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("user")
        val userid = intent.getStringExtra("userid")
        if (userid != null) {
            curretid = userid.toString()
        } else {
            curretid = Firebase.auth.currentUser!!.uid
        }
        if (curretid!= Firebase.auth.currentUser!!.uid) {
            binding.editprofile.visibility = View.GONE
        }
        FirebaseFirestore.getInstance().collection("user").document(Firebase.auth.currentUser!!.uid)
             .get().addOnSuccessListener {
                if (it.data!!["role"].toString()=="Admin")
                {
                    binding.spinner.visibility = View.VISIBLE
                }
            }
        usersCollection.document(curretid).get()
            .addOnSuccessListener { querySnapshot ->

                if (querySnapshot != null) {
                    binding.name.text = querySnapshot.data!!["name"].toString()
                    binding.email.text = querySnapshot.data!!["email"].toString()
                    binding.gender.text = querySnapshot.data!!["gender"].toString()
                    binding.phoneNumber.text = querySnapshot.data!!["phone_number"].toString()
                    binding.dob.text = querySnapshot.data!!["date_of_birth"].toString()
                    binding.userid.text = Firebase.auth.currentUser!!.uid
                    if (querySnapshot.data!!["image"] != null) {
                        Picasso.get().load(querySnapshot.data!!["image"].toString())
                            .placeholder(R.drawable.profile).into(binding.image)
                    }
                }
            }
        binding.editprofile.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Signup::class.java)
            intent.putExtra("message", "Editprofile")
            startActivity(intent)
        })

        val spinner: Spinner = binding.spinner

        // Define options
        val options = arrayOf("Admin", "User", "Employee")

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        spinner.adapter = adapter

        // Listen for item selection
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedItem = parent?.getItemAtPosition(position).toString()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Are you sure you want to select $selectedItem?")
        builder.setPositiveButton("Yes") { dialog, which ->
            // Handle "Yes" button click
            selectedItem?.let {
                val updates = hashMapOf<String, Any>(
                    "role" to selectedItem.toString()
                )
                FirebaseFirestore.getInstance().collection("user").document(curretid).update(updates).addOnSuccessListener {
                    Toast.makeText(this,"Role Updated",Toast.LENGTH_SHORT).show()
                }
            }
            dialog.dismiss() // Dismiss the dialog after processing
        }
        builder.setNegativeButton("No") { dialog, which ->
            // Handle "No" button click or dialog cancellation
            dialog.dismiss() // Dismiss the dialog without processing
        }
        builder.show()


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Handle case when nothing is selected
    }




}


