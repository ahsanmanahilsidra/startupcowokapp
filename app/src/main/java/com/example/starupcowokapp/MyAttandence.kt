package com.example.starupcowokapp

import Adopters.AttandenceAdapter
import Adopters.CommunityAdapter
import Models.Attandence
import Models.user
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.starupcowokapp.databinding.ActivityMyAttandenceBinding
import com.example.starupcowokapp.databinding.FragmentCommunityBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class MyAttandence : AppCompatActivity() {
    var AttandenceList=ArrayList<Attandence>()
    private lateinit var adapter: AttandenceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding by lazy {
            ActivityMyAttandenceBinding.inflate(layoutInflater)
        }
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adapter= AttandenceAdapter(this,AttandenceList)
        binding.recyclerView.layoutManager=
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.adapter=adapter
        Firebase.firestore.collection("Attandence").whereEqualTo("userid",Firebase.auth.currentUser!!.uid).get().addOnSuccessListener(){
            var templist= arrayListOf<Attandence>()
            for(i in it.documents){
                var Attandence: Attandence= i.toObject<Attandence>()!!
                templist.add(Attandence)
            }
            templist.reverse()
            AttandenceList.addAll(templist)
            adapter.notifyDataSetChanged();
        }
    }
}