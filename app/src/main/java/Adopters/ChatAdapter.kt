package Adopters

import Models.user
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.ChatHolderBinding
import com.squareup.picasso.Picasso
import utils.openWhatsAppChat

class ChatAdapter(var context: Context, var chatlist: ArrayList<user>): RecyclerView.Adapter<ChatAdapter.ViewHolder>()  {
    inner class ViewHolder(var binding: ChatHolderBinding) : RecyclerView.ViewHolder(binding.root) {
    }
    fun setfilterList(communitylist: ArrayList<user>){
        this.chatlist=communitylist
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ChatHolderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return chatlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(chatlist.get(position).Image).placeholder(R.drawable.loading).into(holder.binding.image)
        holder.binding.name.setText(chatlist.get(position).Name)
        holder.binding.chatholder.setOnClickListener(View.OnClickListener {
            openWhatsAppChat(context,chatlist.get(position).Phone_number.toString())
        })
    }
}

