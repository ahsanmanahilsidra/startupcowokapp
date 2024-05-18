package Fragments

import Models.post
import Models.user
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.starupcowokapp.Home
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.FragmentAddpostBinding
import com.example.starupcowokapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import constants.user_post_directory
import uploadImage
import utils.createnotification
import java.io.ByteArrayOutputStream


class Addpost : DialogFragment() {
    private val REQUEST_IMAGE_CAPTURE = 1
    private var imageUri: Uri? = null
    private lateinit var bindingFragment: FragmentAddpostBinding

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
//

//
    }


    var imgulr: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            bindingFragment.Img.setImageResource(R.drawable.loading)
            uploadImage(requireContext(), uri, user_post_directory) {

                if (it != null) {
                    bindingFragment.Img.setImageURI(uri)
                    imgulr = it.toString()
                }

            }
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postId = arguments?.getString(ARG_POST_ID)
        if (postId != null) {
            bindingFragment.title.setText("Edit Post")
            bindingFragment.post.setText("Update")
            FirebaseFirestore.getInstance().collection("Post").document(postId.toString()).get()
                .addOnSuccessListener {
                    if (imgulr==null) {
                        imgulr = it.data!!["posturl"].toString()
                    }
                    Picasso.get().load(it.data!!["posturl"].toString())
                        .placeholder(R.drawable.loading)
                        .into(bindingFragment.Img)
                    bindingFragment.discription.setText(it.data!!["caption"].toString())
                }

            bindingFragment.post.setOnClickListener(View.OnClickListener {
                val updates = hashMapOf<String, Any>(
                    "posturl" to imgulr.toString(),
                    "caption" to bindingFragment.discription.text.toString()
                )
                FirebaseFirestore.getInstance().collection("Post").document(postId.toString()).update(updates)
                    .addOnSuccessListener {
                        Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show()
                        dialog?.dismiss()
                    }
            })

        }
        if (postId == null) {
            bindingFragment.post.setOnClickListener() {


                if (imgulr != null) {
                    val Postid =
                        FirebaseFirestore.getInstance().collection(user_post_directory)
                            .document().id
                    val userpost: post = post(imgulr!!, bindingFragment.discription.text.toString())
                    val post: post = post(
                        imgulr!!,
                        bindingFragment.discription.text.toString(),
                        Firebase.auth.currentUser!!.uid,
                        System.currentTimeMillis().toString(),
                        Postid
                    )


                    Firebase.firestore.collection(user_post_directory).document(Postid).set(post)
                        .addOnSuccessListener() {

                            Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + "post")
                                .document().set(userpost).addOnSuccessListener() {
                                    Firebase.firestore.collection("user")
                                        .document(FirebaseAuth.getInstance().currentUser!!.uid)
                                        .get()
                                        .addOnSuccessListener {
                                            if (it != null) {
                                                var user = it.toObject<user>()!!
                                                var name = user.Name.toString()
                                                var img = user.Image.toString()
                                                createnotification(
                                                    "Added post",
                                                    "${name}  Add a post",
                                                    "Home()",
                                                    "all",
                                                    FirebaseAuth.getInstance().currentUser!!.uid,
                                                    img
                                                )
                                            }
                                        }


                                    dialog?.dismiss()
                                }
                        }
                }
            }


        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingFragment = FragmentAddpostBinding.inflate(inflater, container, false)
        bindingFragment.picture.setOnClickListener(View.OnClickListener {
            launcher.launch("image/*")
        })

        bindingFragment.cross.setOnClickListener(View.OnClickListener {
            dialog?.dismiss()
        })


        bindingFragment.camera.setOnClickListener(View.OnClickListener {
            dispatchTakePictureIntent()
        })


        return bindingFragment.root
    }


    companion object {

        private const val ARG_POST_ID = "postId"

        fun newInstance(postId: String): Addpost {
            val fragment = Addpost()
            val args = Bundle()
            args.putString(ARG_POST_ID, postId)
            fragment.arguments = args
            return fragment
        }
    }
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageUri = getImageUri(imageBitmap)
            uploadImage(requireContext(), imageUri!!, user_post_directory) {
                if (it != null) {
                    bindingFragment.Img.setImageURI(imageUri)
                    imgulr = it.toString()
                    bindingFragment.Img.setImageURI(imageUri)
                }

            }
        }
    }

    private fun getImageUri(inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context?.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }


}