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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
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
        bindingFragment = FragmentCreateEventBinding.inflate(inflater, container, false)
        val eventId = arguments?.getString(CreateEvent.ARG_EVENT_ID)
        bindingFragment.picture.setOnClickListener(View.OnClickListener {
            launcher.launch("image/*")
        })

        bindingFragment.cross.setOnClickListener(View.OnClickListener {
            dialog?.dismiss()
        })
        if (eventId ==null) {
            bindingFragment.post.setOnClickListener() {
                var id = Firebase.firestore.collection("Event").document().id
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

                } else {
                    if (bindingFragment.title.text.toString() == "" || bindingFragment.discription.text.toString() == "") {
                        Toast.makeText(
                            context,
                            "Title and decription will not be empety",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
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
        }
        if (eventId !=null) {
            bindingFragment.head.setText("Update Event")
            bindingFragment.post.setText("Update")
            FirebaseFirestore.getInstance().collection("Event").document(eventId.toString()).get()
                .addOnSuccessListener {
                    bindingFragment.title.setText(it.data!!["eventtitle"].toString())
                    bindingFragment.discription.setText(it.data!!["eventdiscription"].toString())
                    bindingFragment.location.setText(it.data!!["eventlocation"].toString())
                    bindingFragment.eventtype.setText(it.data!!["eventtype"].toString())
                    bindingFragment.dateandtime.setText(it.data!!["eventdate"].toString())
                    if (imgulr=="") {
                        imgulr = it.data!!["eventurl"].toString()
                    }
                    try {
                        Picasso.get().load(it.data!!["eventurl"].toString())
                            .placeholder(R.drawable.loading)
                            .into(bindingFragment.Img)
                    }
                    finally {

                    }
                }
            bindingFragment.post.setOnClickListener() {
                if (imgulr != null) {
                    val updates = hashMapOf<String, Any>(
                        "eventdate" to bindingFragment.dateandtime.text.toString(),
                        "eventdiscription" to bindingFragment.discription.text.toString(),
                        "eventlocation" to bindingFragment.location.text.toString(),
                        "eventtitle" to bindingFragment.title.text.toString(),
                        "eventtype" to bindingFragment.eventtype.text.toString(),
                        "eventurl" to imgulr.toString()
                    )
                    Firebase.firestore.collection("Event").document(eventId.toString()).update(updates)
                        .addOnSuccessListener() {
                            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
                            dialog?.dismiss()
                        }

                } else {
                    if (bindingFragment.title.text.toString() == "" || bindingFragment.discription.text.toString() == "") {
                        Toast.makeText(
                            context,
                            "Title and decription will not be empety",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val updates = hashMapOf<String, Any>(
                            "eventdate" to bindingFragment.dateandtime.text.toString(),
                            "eventdiscription" to bindingFragment.discription.text.toString(),
                            "eventlocation" to bindingFragment.location.text.toString(),
                            "eventtitle" to bindingFragment.title.text.toString(),
                            "eventtype" to bindingFragment.eventtype.text.toString(),

                            )
                        Firebase.firestore.collection("Event").document(eventId).update(updates)
                            .addOnSuccessListener() {
                                Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
                                dialog?.dismiss()
                            }
                    }
                }

            }
        }

        return bindingFragment.root

    }

    companion object {

        private const val ARG_EVENT_ID = "eventId"

        fun newInstance(postId: String): CreateEvent {
            val fragment = CreateEvent()
            val args = Bundle()
            args.putString(ARG_EVENT_ID, postId)
            fragment.arguments = args
            return fragment
        }
    }

}