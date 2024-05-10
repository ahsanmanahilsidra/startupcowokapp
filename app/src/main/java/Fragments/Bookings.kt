package Fragments

import Adopters.BookingAdopter
import Models.Booking
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.starupcowokapp.databinding.FragmentBookingsBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class Bookings : Fragment() {
    private lateinit var bindingFragment: FragmentBookingsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bookingList=ArrayList<Booking>()
        var adapter= BookingAdopter(requireContext(),bookingList)
        bindingFragment.recilerview.layoutManager=
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        bindingFragment.recilerview.adapter=adapter
        Firebase.firestore.collection("Booking").get().addOnSuccessListener(){
            var templist= arrayListOf<Booking>()
            for(i in it.documents){
                var booking: Booking = i.toObject<Booking>()!!
                templist.add(booking)
            }
            templist.reverse()
            bookingList.addAll(templist)
            adapter.notifyDataSetChanged();
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingFragment = FragmentBookingsBinding.inflate(inflater, container, false)
        bindingFragment.AddBooking.setOnClickListener(/* l = */ View.OnClickListener {


        })


        return bindingFragment.root
    }
    companion object {

    }
}