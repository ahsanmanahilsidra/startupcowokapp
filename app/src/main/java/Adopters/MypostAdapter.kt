package Adopters

import Models.post
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.MypostholderBinding
import com.example.starupcowokapp.databinding.PostHolderBinding
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class MypostAdapter (var context: Context, var postlist: ArrayList<post>): RecyclerView.Adapter<MypostAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: MypostholderBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = MypostholderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get().load(postlist.get(position).posturl).placeholder(R.drawable.loading).into(holder.binding.img)

    }
}