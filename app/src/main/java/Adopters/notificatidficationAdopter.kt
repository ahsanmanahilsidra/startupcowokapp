package Adopters

import Models.notification
import Models.user
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.example.starupcowokapp.Home
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.ChatHolderBinding
import com.example.starupcowokapp.databinding.NotificationholderBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso
import utils.openWhatsAppChat

class notificatidficationAdopter(
    var context: Context,
    var notificationlist: ArrayList<notification>,
) : RecyclerView.Adapter<notificatidficationAdopter.ViewHolder>() {
    inner class ViewHolder(var binding: NotificationholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    fun setfilterList(notificationLIst: ArrayList<notification>) {
        this.notificationlist = notificationLIst
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = NotificationholderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notificationlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if (notificationlist.get(position).from != null) {
//            FirebaseFirestore.getInstance().collection("user")
//                .document(notificationlist.get(position).from).get().addOnSuccessListener {
//                    Picasso.get().load(it.data!!["image"].toString()).placeholder(R.drawable.loading)
//                        .into(holder.binding.image)
//            }
//        }
//        else{
//            FirebaseFirestore.getInstance().collection("user")
//                .document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
//
//                    Picasso.get().load(it.data!!["image"].toString()).placeholder(R.drawable.loading)
//                        .into(holder.binding.image)
//                }
//        }
        holder.binding.title.setText(notificationlist.get(position).title)
        holder.binding.message.setText(notificationlist.get(position).message)
        Picasso.get().load(notificationlist.get(position).imgurl).into(holder.binding.image)
        holder.binding.layout.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, Home()::class.java)
            intent.putExtra("fragmentTag", notificationlist.get(position).fragment)
            context.startActivity(intent)
        })
    }
}

