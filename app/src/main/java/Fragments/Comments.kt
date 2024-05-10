package Fragments

import Adopters.CommentAdapter
import Models.comment
import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.trusted.Token
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.starupcowokapp.Home
import com.example.starupcowokapp.MainActivity
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.FragmentCommentsBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.messaging.FirebaseMessaging
import org.w3c.dom.Comment
import utils.createnotification

private const val CHANNEL_ID = "my_app_channel"
private const val NOTIFICATION_ID = 123

class Comments : BottomSheetDialogFragment() {
    private lateinit var bindingFragment: FragmentCommentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onStart() {
        super.onStart()
        bindingFragment.comment.requestFocus()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val postId = arguments?.getString("postId")
        bindingFragment = FragmentCommentsBinding.inflate(inflater, container, false)
        bindingFragment.commentupload.setOnClickListener(View.OnClickListener {

            var commentid: String = Firebase.firestore.collection("Comments").document().id
            val Comment: comment = comment(
                bindingFragment.comment.text.toString(),
                Firebase.auth.currentUser!!.uid, postId.toString(),
                commentid,
                commentid,
                System.currentTimeMillis().toString()
            )


            var userid = ""

            Firebase.firestore.collection("Comments").document(commentid).set(Comment)
                .addOnSuccessListener {
                    FirebaseFirestore.getInstance().collection("Post").document(postId.toString())
                        .get()
                        .addOnSuccessListener {

                            if (it != null) {
                                userid = it.data!!["userid"].toString()
                                FirebaseFirestore.getInstance().collection("user").document(Firebase.auth.currentUser!!.uid)
                                    .get()
                                    .addOnSuccessListener {

                                        if (it != null) {
                                            var name = it.data!!["name"].toString()
                                            var img =it.data!!["image"].toString()
                                            createnotification("Commented","${name}  Commented on your Post","Home()",
                                                userid,FirebaseAuth.getInstance().currentUser!!.uid,img)
                                        }
                                    }
                            }
                        }
                    Toast.makeText(context, "comment posted", Toast.LENGTH_SHORT).show()

                    bindingFragment.comment.clearFocus()
                }
        })

        var commentList = ArrayList<comment>()
        var adapter = CommentAdapter(requireContext(), commentList)
        bindingFragment.recilerview.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        bindingFragment.recilerview.adapter = adapter
        Firebase.firestore.collection("Comments").whereEqualTo("postid", postId)
            .addSnapshotListener() { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    // Handle error
                    return@addSnapshotListener
                }

                querySnapshot?.let { snapshot ->
                    var templist = arrayListOf<comment>()
                    for (document in snapshot.documents) {
                        val comment: comment = document.toObject<comment>() ?: continue
                        templist.add(comment)
                    }
                    templist.reverse()
                    commentList.clear()
                    commentList.addAll(templist)
                    adapter.notifyDataSetChanged()
                }
            }
        return bindingFragment.root
    }

    companion object {
        fun newInstance(postId: String): Comments {
            val fragment = Comments()
            val args = Bundle()
            args.putString("postId", postId)
            fragment.arguments = args
            return fragment
        }
    }




}