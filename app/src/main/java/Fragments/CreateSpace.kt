package Fragments

import Models.Space
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.FragmentCreateSpaceBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
            uploadImage(requireContext(),uri, user_post_directory) {
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

        bindingFragment.picture.setOnClickListener(View.OnClickListener {
            launcher.launch("image/*")
        })

        bindingFragment.cross.setOnClickListener(View.OnClickListener {
            dialog?.dismiss()
        })
        bindingFragment.post.setOnClickListener() {
            var id=  Firebase.firestore.collection("space").document().id
            if (imgulr != null) {
                val space: Space = Models.Space(
                    imgulr!!,
                    bindingFragment.title.text.toString(),
                    id,
                    bindingFragment.price.text.toString(),
                    bindingFragment.discription.text.toString(),

                    )
                Firebase.firestore.collection("space").document(id).set(space)
                    .addOnSuccessListener() {
                        dialog?.dismiss()
                    }

            }
        }


        return  bindingFragment.root

    }


}