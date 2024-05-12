package Fragments

import Adopters.CommunityAdapter
import Models.Event
import Models.Space
import Models.user
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.FragmentCommunityBinding
import com.example.starupcowokapp.databinding.FragmentCreateEventBinding
import com.example.starupcowokapp.databinding.FragmentCreateSpaceBinding
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import constants.user_post_directory
import uploadImage

import java.util.Locale
private lateinit var bindingFragment: FragmentCreateEventBinding

class CreateEvent : DialogFragment() {
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
        bindingFragment = FragmentCreateEventBinding.inflate(inflater, container, false)

        bindingFragment.picture.setOnClickListener(View.OnClickListener {
            launcher.launch("image/*")
        })

        bindingFragment.cross.setOnClickListener(View.OnClickListener {
            dialog?.dismiss()
        })
        bindingFragment.post.setOnClickListener() {
            var id=  Firebase.firestore.collection("Event").document().id
            if (imgulr != null) {
                val event: Event = Event(
                    imgulr!!,
                    bindingFragment.title.text.toString(),
                    bindingFragment.discription.text.toString(),
                    bindingFragment.dateandtime.text.toString(),
                    bindingFragment.location.text.toString(),
                    bindingFragment.eventtype.text.toString(),
                    id.toString()

                )
                Firebase.firestore.collection("Event").document(id).set(event)
                    .addOnSuccessListener() {
                        dialog?.dismiss()
                    }

            }
            else{
                if (bindingFragment.title.text.toString()==""|| bindingFragment.discription.text.toString()=="")
                {
                    Toast.makeText(context,"Title and decription will not be empety",Toast.LENGTH_SHORT).show()
                }
                else {
                    val event: Event = Event(
                        bindingFragment.title.text.toString(),
                        bindingFragment.discription.text.toString(),
                        bindingFragment.dateandtime.text.toString(),
                        bindingFragment.location.text.toString(),
                        bindingFragment.eventtype.text.toString(),
                        id.toString()

                    )
                    Firebase.firestore.collection("Event").document(id).set(event)
                        .addOnSuccessListener() {
                            dialog?.dismiss()
                        }
                }
            }

        }


        return  bindingFragment.root

    }


}