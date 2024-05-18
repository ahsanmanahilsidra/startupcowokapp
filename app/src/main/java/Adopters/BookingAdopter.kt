package Adopters

import Models.Booking
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.BookingHolderBinding
import com.squareup.picasso.Picasso

class BookingAdopter(var context: Context, var bookinglist:ArrayList<Booking>):RecyclerView.Adapter<BookingAdopter.ViewHolder>() {
    inner class ViewHolder(var binding: BookingHolderBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = BookingHolderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return bookinglist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}