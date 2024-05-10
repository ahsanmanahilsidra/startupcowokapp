package Fragments

import Adopters.MypostAdapter
import Adopters.PostAdapter
import Models.Like
import Models.post
import Models.user
import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.set
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.starupcowokapp.Account_detail
import com.example.starupcowokapp.Login
import com.example.starupcowokapp.MyAttandence
import com.example.starupcowokapp.R
import com.example.starupcowokapp.Scaner
import com.example.starupcowokapp.databinding.FragmentProfileBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.google.zxing.BarcodeFormat
import com.google.zxing.Writer
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.encoder.QRCode
import com.squareup.picasso.Picasso
import constants.user_node
import constants.user_post_directory


class profile : Fragment() {
    private var camerapermition:ActivityResultLauncher<String>?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private lateinit var bindingFragment: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): ConstraintLayout {

        bindingFragment = FragmentProfileBinding.inflate(layoutInflater, container, false)
        Firebase.firestore.collection(user_node).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                if (it!= null) {
                    Picasso.get().load(it.data!!["image"].toString()).placeholder(R.drawable.profile)
                        .into(bindingFragment.profileimg)
                    bindingFragment.name.setText(it.data!!["name"].toString())
                }
            }


        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + "post").get()
            .addOnSuccessListener() {
                var count = 0
                for (doc in it ){
                    count++
                }
                bindingFragment.posts.setText(count.toString())

            }
        bindingFragment.logout.setOnClickListener(View.OnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(context, Login::class.java))

        })
        bindingFragment.Attendance.setOnClickListener(View.OnClickListener {
            camerapermition?.launch(Manifest.permission.CAMERA)
        })
        camerapermition=registerForActivityResult(ActivityResultContracts.RequestPermission(),){
            if (it){
                startActivity(Intent(context,Scaner::class.java))
            }
            else{
                Toast.makeText(context,"Camera Permission Not Granted",Toast.LENGTH_SHORT).show()
            }
        }
        bindingFragment.changeemail.setOnClickListener(View.OnClickListener {
            val dialogFragment = Change_email()
            dialogFragment.show(parentFragmentManager, "MyDialogFragmentTag")
        })
        bindingFragment.changePassword.setOnClickListener(View.OnClickListener {
            val dialogFragment = Change_password()
            dialogFragment.show(parentFragmentManager, "MyDialogFragmentTag")
        })
        bindingFragment.Acoountdetails.setOnClickListener(View.OnClickListener {
            startActivity(Intent(context,Account_detail::class.java))
        })
        bindingFragment.mybookings.setOnClickListener(View.OnClickListener {
            Toast.makeText(context,"No Bookings",Toast.LENGTH_SHORT).show()
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
                Log.d(TAG, token)

            })

        })
 bindingFragment.Myattandence.setOnClickListener(View.OnClickListener {
 startActivity(Intent(context,MyAttandence::class.java))
 })
        bindingFragment.showqrcode.setOnClickListener(View.OnClickListener {
            val dialogFragment =showqrcode()
            dialogFragment.show(parentFragmentManager, "MyDialogFragmentTag")
        })
        return bindingFragment.root
    }



}
