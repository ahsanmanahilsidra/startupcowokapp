package Adopters

import Models.Event
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starupcowokapp.Booking
import com.example.starupcowokapp.Eventdetail
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.EventHolderBinding
import com.squareup.picasso.Picasso

class EventAdopter(var context: Context, var eventlist: ArrayList<Event>):RecyclerView.Adapter<EventAdopter.ViewHolder>(){
    inner class ViewHolder(var binding: EventHolderBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = EventHolderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return eventlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get().load(eventlist.get(position).Eventurl).placeholder(R.drawable.loading).into(holder.binding.eventimd)
        holder.binding.title.setText(eventlist.get(position).Eventtitle)
        holder.binding.type.setText(eventlist.get(position).eventtype)
        holder.binding.location.setText(eventlist.get(position).eventlocation)
        holder.binding.dateAndTime.setText(eventlist.get(position).eventdate)
holder.binding.event.setOnClickListener(View.OnClickListener {
    val intent= Intent(context, Eventdetail::class.java)
    intent.putExtra("id",eventlist.get(position).eventid)
    context.startActivity(intent)
})
    }
}