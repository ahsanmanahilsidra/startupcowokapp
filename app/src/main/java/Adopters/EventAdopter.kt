package Adopters

import Fragments.CreateEvent
import Fragments.CreateSpace
import Models.Event
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.starupcowokapp.Booking
import com.example.starupcowokapp.Eventdetail
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.EventHolderBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class EventAdopter(var context: Context, var eventlist: ArrayList<Event>,private val fragmentManager: FragmentManager):RecyclerView.Adapter<EventAdopter.ViewHolder>(){
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
        holder.binding.delet2.visibility=View.GONE
        holder.binding.edit2.visibility=View.GONE
        FirebaseFirestore.getInstance().collection("user").document(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener {
                if (it.data!!["role"].toString()=="Admin"||it.data!!["role"].toString()=="Employee")
                {
                    holder.binding.delet2.visibility = View.VISIBLE
                    holder.binding.edit2.visibility = View.VISIBLE
                }
            }
       if (eventlist.get(position).Eventurl!="") {
           Picasso.get().load(eventlist.get(position).Eventurl).placeholder(R.drawable.loading)
               .into(holder.binding.eventimd)
       }
        else{

            holder.binding.eventimd.visibility=View.GONE
       }
        holder.binding.title.setText(eventlist.get(position).Eventtitle)
        holder.binding.type.setText(eventlist.get(position).eventtype)
        holder.binding.location.setText(eventlist.get(position).eventlocation)
        holder.binding.dateAndTime.setText(eventlist.get(position).eventdate)
holder.binding.event.setOnClickListener(View.OnClickListener {
    val intent= Intent(context, Eventdetail::class.java)
    intent.putExtra("id",eventlist.get(position).eventid)
    context.startActivity(intent)
})
        holder.binding.delet2.setOnClickListener(View.OnClickListener {
            FirebaseFirestore.getInstance().collection("Event").document(eventlist[position].eventid)
                .delete().addOnSuccessListener {
                    Toast.makeText(context,"deleted sucessfully", Toast.LENGTH_SHORT).show()
                }
        })
        holder.binding.edit2.setOnClickListener(View.OnClickListener {
            val eventId = eventlist.get(position).eventid
            val dialogFragment = CreateEvent.newInstance(eventId)
            dialogFragment.show(fragmentManager, "YourDialogTag")
        })
    }
}