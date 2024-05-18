package Adopters

import Fragments.Addpost
import Fragments.Change_email
import Fragments.Comments
import Fragments.showimg
import Models.Like
import Models.comment
import Models.post
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.PostHolderBinding
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.android.gms.internal.phenotype.zzh.init
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso
import kotlinx.coroutines.NonDisposableHandle.parent

class PostAdapter(
    var context: Context,
    var postlist: ArrayList<post>,
    private val fragmentManager: FragmentManager,
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: PostHolderBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = PostHolderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("user")

        usersCollection.document(postlist.get(position).userid).get().addOnSuccessListener() {
            if (it != null) {
                holder.binding.name.setText(it.data!!["name"].toString())
                if (it.data!!["image"] != null) {
                    Picasso.get().load(it.data!!["image"].toString())
                        .placeholder(R.drawable.profile).into(holder.binding.image)
                }
            }
        }
        val text: String = TimeAgo.using(postlist.get(position).time.toLong())

        holder.binding.time.setText(text)

        Picasso.get().load(postlist.get(position).posturl).placeholder(R.drawable.loading)
            .into(holder.binding.postimg)
        holder.binding.discription.setText(postlist.get(position).caption)
        var checklike = false
        // Define a SharedPreferences variable
        val sharedPreferences = context.getSharedPreferences("like_status", Context.MODE_PRIVATE)

// Read the like status from SharedPreferences
        var isLiked = sharedPreferences.getBoolean("liked_${postlist[position].postid}", false)

        holder.binding.like.setImageResource(if (isLiked) R.drawable.readheart else R.drawable.heart)

        holder.binding.like.setOnClickListener(View.OnClickListener {
            if (!isLiked) {
                var likeid = Firebase.firestore.collection("Like")
                    .document().id // Use id instead of toString()
                var like: Like =
                    Like(likeid, Firebase.auth.currentUser!!.uid, postlist[position].postid)
                Firebase.firestore.collection("Like").document(likeid).set(like)
                    .addOnSuccessListener {
                        // Update the like status in SharedPreferences
                        sharedPreferences.edit()
                            .putBoolean("liked_${postlist[position].postid}", true).apply()
                        holder.binding.like.setImageResource(R.drawable.readheart)
                    }
                isLiked = true
            } else {
                Firebase.firestore.collection("Like")
                    .whereEqualTo("userid", Firebase.auth.currentUser!!.uid)
                    .whereEqualTo("postid", postlist[position].postid)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        for (document in querySnapshot.documents) {
                            db.collection("Like").document(document.id).delete()
                                .addOnSuccessListener {
                                    // Update the like status in SharedPreferences
                                    sharedPreferences.edit()
                                        .putBoolean("liked_${postlist[position].postid}", false)
                                        .apply()
                                    holder.binding.like.setImageResource(R.drawable.heart)
                                }
                        }
                    }
                isLiked = false
            }
        })

        holder.binding.comment.setOnClickListener(View.OnClickListener {
            val postId = postlist[position].postid
            val dialogFragment = Comments.newInstance(postId)
            dialogFragment.show(fragmentManager, "CommentsDialog")

        })
        Firebase.firestore.collection("Like").whereEqualTo("postid", postlist.get(position).postid)
            .addSnapshotListener() { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    // Handle error
                    return@addSnapshotListener
                }

                querySnapshot?.let { snapshot ->
                    var count = 0
                    for (document in snapshot.documents) {
                        count++
                    }
                    holder.binding.likeamount.setText(count.toString())
                }
            }

        holder.binding.postimg.setOnClickListener(View.OnClickListener {
            val posturl = postlist[position].posturl
            val dialogFragment = showimg.newInstance(posturl)
            dialogFragment.show(fragmentManager, "CommentsDialog")

        })
        holder.binding.delet.setOnClickListener(View.OnClickListener {
            FirebaseFirestore.getInstance().collection("Post").document(postlist[position].postid)
                .delete().addOnSuccessListener {
                    Toast.makeText(context,"deleted sucessfully",Toast.LENGTH_SHORT).show()
                }
        })
        holder.binding.edit.setOnClickListener {
            val postId = postlist.get(position).postid
            val dialogFragment = Addpost.newInstance(postId)
            dialogFragment.show(fragmentManager, "YourDialogTag")

        }
        holder.binding.delet.visibility=View.GONE
        holder.binding.edit.visibility=View.GONE
        FirebaseFirestore.getInstance().collection("user").document(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener {
                if (it.data!!["role"].toString()=="Admin"||postlist.get(position).userid==Firebase.auth.currentUser!!.uid)
                {
                    holder.binding.delet.visibility = View.VISIBLE
                    holder.binding.edit.visibility = View.VISIBLE
                }
            }
    }


}