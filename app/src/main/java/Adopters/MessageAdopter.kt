package Adopters

import Models.notification
import Models.user
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starupcowokapp.Home
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.CommentHolderBinding
import com.example.starupcowokapp.databinding.CommunityHolderBinding
import com.example.starupcowokapp.databinding.NotificationholderBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import utils.openWhatsAppChat

class MessageAdopter (var context: Context, var messagelist: ArrayList<user>):RecyclerView.Adapter<MessageAdopter.ViewHolder>()  {
    inner class ViewHolder(var binding: CommunityHolderBinding) : RecyclerView.ViewHolder(binding.root) {
    }
    fun setfilterList(communitylist: ArrayList<user>){
        this.messagelist=communitylist
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdopter.ViewHolder {
        var binding = CommunityHolderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(messagelist.get(position).Image).placeholder(R.drawable.loading).into(holder.binding.profileimg)
        holder.binding.Name.setText(messagelist.get(position).Name)
        holder.binding.email.setText(messagelist.get(position).Email)
        holder.binding.contect.setText(messagelist.get(position).Phone_number)
        holder.binding.message.setOnClickListener(View.OnClickListener {
            openWhatsAppChat(context,messagelist.get(position).Phone_number.toString())
        })
    }

    override fun getItemCount(): Int {
        return messagelist.size
    }




}