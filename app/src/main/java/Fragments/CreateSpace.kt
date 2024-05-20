package Fragments

import Models.Space
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.FragmentCreateSpaceBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import constants.user_post_directory
import uploadImage

private lateinit var bindingFragment: FragmentCreateSpaceBinding

class CreateSpace : DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

    }

    var imgulr: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(requireContext(), uri, user_post_directory) {
                if (it != null) {
                    bindingFragment.Img.setImageURI(uri)
                    imgulr = it.toString()

                }

            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingFragment = FragmentCreateSpaceBinding.inflate(inflater, container, false)
        val spaceId = arguments?.getString(CreateSpace.ARG_SPACE_ID)

        bindingFragment.picture.setOnClickListener(View.OnClickListener {
            launcher.launch("image/*")
        })

        bindingFragment.cross.setOnClickListener(View.OnClickListener {
            dialog?.dismiss()
        })
        if (spaceId ==null) {
            bindingFragment.post.setOnClickListener() {
                var id = Firebase.firestore.collection("space").document().id

                if (imgulr != null) {
                    val space: Space = Models.Space(
                        imgulr!!,
                        bindingFragment.title.text.toString(),
                        id,
                        bindingFragment.price.text.toString(),
                        bindingFragment.discription.text.toString(),
                        bindingFragment.seats.text.toString(),
                        bindingFragment.seats.text.toString(),
                    )
                    Firebase.firestore.collection("space").document(id).set(space)
                        .addOnSuccessListener() {
                            dialog?.dismiss()
                        }

                }
            }

        }
        if (spaceId!=null) {
            bindingFragment.head.setText("Edit Space")
            bindingFragment.post.setText("Update")
            FirebaseFirestore.getInstance().collection("space").document(spaceId.toString()).get()
                .addOnSuccessListener {
                    bindingFragment.title.setText(it.data!!["spacetitle"].toString())
                    bindingFragment.discription.setText(it.data!!["about"].toString())
                    bindingFragment.seats.setText(it.data!!["totalseats"].toString())
                    bindingFragment.price.setText(it.data!!["price"].toString())
                    if (imgulr==null) {
                        imgulr = it.data!!["spaceurl"].toString()
                    }
                    Picasso.get().load(it.data!!["spaceurl"].toString())
                        .placeholder(R.drawable.loading)
                        .into(bindingFragment.Img)
                }

            bindingFragment.post.setOnClickListener() {

                if (imgulr != null) {

                    val updates = hashMapOf<String, Any>(

                        "spaceurl" to imgulr.toString(),
                        "spacetitle" to bindingFragment.title.text.toString(),
                        "price" to bindingFragment.price.text.toString(),
                        "totalseats" to bindingFragment.seats.text.toString(),
                        "about" to bindingFragment.discription.text.toString(),

                        )
                    FirebaseFirestore.getInstance().collection("space").document(spaceId.toString())
                        .update(updates)
                        .addOnSuccessListener() {
                            dialog?.dismiss()
                            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
                        }

                }
            }
        }


        return bindingFragment.root

    }

    companion object {

        private const val ARG_SPACE_ID = "spaceId"

        fun newInstance(postId: String): CreateSpace {
            val fragment = CreateSpace()
            val args = Bundle()
            args.putString(ARG_SPACE_ID, postId)
            fragment.arguments = args
            return fragment
        }
    }

}