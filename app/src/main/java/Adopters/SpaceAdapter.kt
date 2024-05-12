package Adopters

import Models.Space
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.starupcowokapp.Booking
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.SpaceHolderBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firestore.admin.v1.Index
import com.squareup.picasso.Picasso

class SpaceAdapter(var context: Context, var spacelist: ArrayList<Space>):RecyclerView.Adapter<SpaceAdapter.ViewHolder>() {
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
        Picasso.get().load(spacelist.get(position).Spaceurl).placeholder(R.drawable.loading).into(holder.binding.spaceimg)
        holder.binding.head.setText(spacelist.get(position).Spacetitle)
        holder.binding.spaceimg.setOnClickListener(View.OnClickListener {
            val intent=Intent(context,Booking::class.java)
            intent.putExtra("id",spacelist.get(position).spaceid)
            context.startActivity(intent)
        })
        holder.binding.delet2.setOnClickListener(View.OnClickListener {
            FirebaseFirestore.getInstance().collection("space").document(spacelist[position].spaceid)
                .delete().addOnSuccessListener {
                    Toast.makeText(context,"deleted sucessfully", Toast.LENGTH_SHORT).show()
                }
        })

    }
}