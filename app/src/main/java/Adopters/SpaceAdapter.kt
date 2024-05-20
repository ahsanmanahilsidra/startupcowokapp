package Adopters

import Fragments.CreateSpace
import Models.Space
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.starupcowokapp.Booking
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.SpaceHolderBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firestore.admin.v1.Index
import com.squareup.picasso.Picasso

class SpaceAdapter(var context: Context, var spacelist: ArrayList<Space>,  private val fragmentManager: FragmentManager):RecyclerView.Adapter<SpaceAdapter.ViewHolder>() {
    inner  class ViewHolder(var binding: SpaceHolderBinding) : RecyclerView.ViewHolder(binding.root) {
    }
    fun setfilterList(spacelist: ArrayList<Space>){
        this.spacelist=spacelist
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = SpaceHolderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return spacelist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (spacelist.get(position).avalabeleseats=="0")
        {
            holder.binding.details.setText("All Space are booked")
            holder.binding.details.setBackgroundColor(ContextCompat.getColor(context, R.color.read))
            holder.binding.space.setBackgroundColor(ContextCompat.getColor(context, R.color.white_light))

        }
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
        Picasso.get().load(spacelist.get(position).Spaceurl).placeholder(R.drawable.loading).into(holder.binding.spaceimg)
        holder.binding.head.setText(spacelist.get(position).Spacetitle)
        holder.binding.spaceimg.setOnClickListener(View.OnClickListener {
            if(spacelist.get(position).avalabeleseats!="0") {
                val intent = Intent(context, Booking::class.java)
                intent.putExtra("id", spacelist.get(position).spaceid)
                context.startActivity(intent)
            }
        })
        holder.binding.delet2.setOnClickListener(View.OnClickListener {
            FirebaseFirestore.getInstance().collection("space").document(spacelist[position].spaceid)
                .delete().addOnSuccessListener {
                    Toast.makeText(context,"deleted sucessfully", Toast.LENGTH_SHORT).show()
                }
        })
        FirebaseFirestore.getInstance().collection("Booking").whereEqualTo("spaceid", spacelist.get(position).spaceid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                // Get the count of documents in the query snapshot
                val count:Int = querySnapshot.size()
                val avalable = spacelist.get(position).totalseats.toInt()-count
                holder.binding.amount.setText("${spacelist.get(position).totalseats.toInt()-avalable}/${spacelist.get(position).totalseats}")

// Update available seats in the database
                val db = FirebaseFirestore.getInstance()
                val spaceRef = db.collection("space").document(spacelist[position].spaceid)

// Update the available seats field in the database
                spaceRef.update("avalabeleseats", avalable.toString())
                    .addOnSuccessListener {
                    }

            }


holder.binding.edit2.setOnClickListener(View.OnClickListener {
    val spaceId = spacelist.get(position).spaceid
    val dialogFragment = CreateSpace.newInstance(spaceId)
    dialogFragment.show(fragmentManager, "YourDialogTag")
})
    }
}