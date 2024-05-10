package Fragments

import Adopters.CommunityAdapter
import Adopters.notificatidficationAdopter
import Models.comment
import Models.notification
import Models.user
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.FragmentMessagesBinding
import com.example.starupcowokapp.databinding.FragmentNotificationBinding
import com.example.starupcowokapp.databinding.FragmentNotificationsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class Notifications : Fragment() {
    lateinit var bindingFragment: FragmentNotificationsBinding
    var notficationlist = ArrayList<notification>()
    lateinit var adapter: notificatidficationAdopter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingFragment = FragmentNotificationsBinding.inflate(layoutInflater, container, false)

        adapter = notificatidficationAdopter(requireContext(), notficationlist)
        bindingFragment.recyclerview.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        bindingFragment.recyclerview.adapter = adapter
        val currentUserUid = Firebase.auth.currentUser!!.uid

        Firebase.firestore.collection("Notification")
            .whereIn("foor", listOf(currentUserUid, "all"))
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    // Handle error
                    return@addSnapshotListener
                }

                querySnapshot?.let { snapshot ->
                    val templist = arrayListOf<notification>()
                    for (document in snapshot.documents) {
                        val notification: notification = document.toObject<notification>() ?: continue
                        templist.add(notification)
                    }
                    notficationlist.clear()
                    notficationlist.addAll(templist)
                    adapter.notifyDataSetChanged()
                }
            }
        return bindingFragment.root
    }

    companion object {


    }
}