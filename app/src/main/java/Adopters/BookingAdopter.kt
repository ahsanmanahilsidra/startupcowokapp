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
        Picasso.get().load(bookinglist.get(position).bookingurl).placeholder(R.drawable.loading).into(holder.binding.bookimg)
        holder.binding.description.setText(bookinglist.get(position).bookingcaption)
        holder.binding.price.setText(bookinglist.get(position).bookingpric)
        holder.binding.seats.setText(bookinglist.get(position).seats)
        holder.binding.title.setText(bookinglist.get(position).title)

    }
}