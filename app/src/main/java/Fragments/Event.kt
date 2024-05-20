package Fragments

import Adopters.EventAdopter
import Models.Event
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.starupcowokapp.databinding.FragmentEventBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class Event : Fragment() {

    private lateinit var bindingFragment: FragmentEventBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingFragment.addevent.visibility=View.GONE

        FirebaseFirestore.getInstance().collection("user").document(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener {
                if (it.data!!["role"].toString()=="Admin"||it.data!!["role"].toString()=="Employee")
                {
                    bindingFragment.addevent.visibility = View.VISIBLE
                }
            }
        var eventList = ArrayList<Event>()
        var adapter = EventAdopter(requireContext(), eventList,requireActivity().supportFragmentManager)
        bindingFragment.recilerview.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        bindingFragment.recilerview.adapter = adapter
        Firebase.firestore.collection("Event").get().addOnSuccessListener() {
            var templist = arrayListOf<Event>()
            for (i in it.documents) {
                var event: Event = i.toObject<Event>()!!
                templist.add(event)
            }
            templist.reverse()
            eventList.addAll(templist)
            adapter.notifyDataSetChanged();
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingFragment = FragmentEventBinding.inflate(inflater, container, false)

        bindingFragment.addevent.setOnClickListener(View.OnClickListener {
            val dialogFragment =CreateEvent()
            dialogFragment.show(parentFragmentManager, "MyDialogFragmentTag")
        })
        return bindingFragment.root
    }


    companion object {

    }
}
