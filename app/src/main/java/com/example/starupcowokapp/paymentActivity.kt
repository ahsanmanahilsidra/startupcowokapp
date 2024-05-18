package com.example.starupcowokapp

import Models.Booking
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.starupcowokapp.databinding.ActivityPayment2Binding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class paymentActivity : AppCompatActivity() {


    val binding by lazy {
        ActivityPayment2Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val id = intent.getStringExtra("spaceid")
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        var name: String = ""
        var price: String = ""
        // Parse the current date string to a Date object
        val calendar = Calendar.getInstance()
        calendar.time =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(currentDate) ?: Date()

        // Add 30 days to the current date
        calendar.add(Calendar.DAY_OF_YEAR, 30)

        // Format the resulting date as a string
        val thirtyDaysLater =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)

        FirebaseFirestore.getInstance().collection("space").document(id.toString()).get()
            .addOnSuccessListener {
                price = it.data!!["price"].toString()
                name = it.data!!["spacetitle"].toString()
                binding.Price.setText(price)

            }
        binding.alfalah.setOnClickListener(View.OnClickListener {
            showdialog(
                currentDate.toString(),
                thirtyDaysLater.toString(),
                id.toString(),
                name,
                price
            )
        })
        binding.ezypasa.setOnClickListener(View.OnClickListener {
            showdialog(
                currentDate.toString(),
                thirtyDaysLater.toString(),
                id.toString(),
                name,
                price
            )
        })
        binding.jazzcash.setOnClickListener(View.OnClickListener {
            showdialog(
                currentDate.toString(),
                thirtyDaysLater.toString(),
                id.toString(),
                name,
                price
            )
        })
    }

    fun showdialog(currentdate: String, thidays: String, id: String, name: String, price: String) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Confirm Booking")
        alertDialog.setMessage("Do you want to book the  ${name} with ${price} price from ${currentdate} to ${thidays}?")
        alertDialog.setPositiveButton("Confirm") { dialog, which ->
            FirebaseFirestore.getInstance().collection("Booking").whereEqualTo("spaceid", id)
                .whereEqualTo("userid", Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                    if (it.isEmpty)
                    {var bookingid = Firebase.firestore.collection("Bookings").document().id
                        val booking: Booking = Booking(
                            bookingid,
                            Firebase.auth.currentUser!!.uid.toString(),
                            id.toString(),
                            name,
                            currentdate,
                            thidays,
                            price
                        )
                        Firebase.firestore.collection("Booking").document(bookingid).set(booking)
                            .addOnSuccessListener {
                                startActivity(Intent(this, bookingcompleted::class.java))
                            }

                    }
                    else{
                        Toast.makeText(this,"Your Space i Alredy Booked ",Toast.LENGTH_SHORT).show()
                    }
                }

        }
        alertDialog.setNegativeButton("Cancel", null)
        alertDialog.show()
    }
}