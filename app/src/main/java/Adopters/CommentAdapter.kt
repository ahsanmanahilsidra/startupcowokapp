package Adopters

import Models.comment
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.CommentHolderBinding
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class CommentAdapter (var context: Context, var commentlist: ArrayList<comment>) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: CommentHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = CommentHolderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return commentlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            FirebaseFirestore.getInstance().collection("user")
                .document(commentlist.get(position).userid).get().addOnSuccessListener {
                    if (it != null) {
                        holder.binding.name.setText(it.data!!["name"].toString())
                        if (it.data!!["image"] != null) {
                            Picasso.get().load(it.data!!["image"].toString())
                                .placeholder(R.drawable.profile).into(holder.binding.image)
                        }
                    }
                }
        val text: String = TimeAgo.using(commentlist.get(position).time.toLong())

        holder.binding.time.setText( text)
        holder.binding.comment.setText(commentlist.get(position).comment)

    }
}

