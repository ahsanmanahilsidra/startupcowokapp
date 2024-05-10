package Fragments

import Adopters.CommunityAdapter
import Adopters.MessageAdopter
import Models.user
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.FragmentMessagesBinding
import com.example.starupcowokapp.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import java.util.Locale


class Messages : Fragment() {
    lateinit var bindingFragment:FragmentMessagesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingFragment = FragmentMessagesBinding.inflate(layoutInflater, container, false)
        val searchView=bindingFragment.searchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
        return bindingFragment.root
    }
    private  fun filterList(query:String?){
        val filterList= ArrayList<user>()
        if(query!=null){
            for (i in messageList){
                if (i.Name?.lowercase(Locale.ROOT)!!.contains(query))
                    filterList.add(i)
            }
            if(filterList.isEmpty()){
                Toast.makeText(context,"no data found", Toast.LENGTH_SHORT).show()
            }
            else{
                adapter.setfilterList(filterList)
            }
        }

    }
    var messageList=ArrayList<user>()
    lateinit var adapter: MessageAdopter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = MessageAdopter(requireContext(), messageList)
        bindingFragment.recilerview.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        bindingFragment.recilerview.adapter = adapter
        Firebase.firestore.collection("user").get().addOnSuccessListener() {
            var templist = arrayListOf<user>()
            for (i in it.documents) {
                var messagelist: user = i.toObject<user>()!!
                templist.add(messagelist)
            }
            templist.reverse()
            messageList.addAll(templist)
            adapter.notifyDataSetChanged();
        }
    }

    companion object {

    }
}