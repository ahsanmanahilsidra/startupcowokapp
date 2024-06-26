package Fragments

import Adopters.SpaceAdapter
import Models.Space
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.starupcowokapp.databinding.FragmentSpaceBinding
import com.google.android.play.integrity.internal.i
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class Space : Fragment()  {
    var spaceList=ArrayList<Space>()
    public lateinit var adapter: SpaceAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
bindingFragment.addspace.visibility=View.GONE
        FirebaseFirestore.getInstance().collection("user").document(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener {
                if (it.data!!["role"].toString()=="Admin"||it.data!!["role"].toString()=="Employee")
                {
                    bindingFragment.addspace.visibility = View.VISIBLE
                }
            }
        adapter= SpaceAdapter(requireContext(),spaceList,requireActivity().supportFragmentManager)
        bindingFragment.recilerview.layoutManager=
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        bindingFragment.recilerview.adapter=adapter
        Firebase.firestore.collection("space").get().addOnSuccessListener(){
            var templist= arrayListOf<Space>()
            for(i in it.documents){
                var space: Space = i.toObject<Space>()!!
                templist.add(space)
            }
            templist.reverse()
            spaceList.addAll(templist)
            adapter.notifyDataSetChanged();
        }

    }

    private lateinit var bindingFragment: FragmentSpaceBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingFragment = FragmentSpaceBinding.inflate(inflater, container, false)

        bindingFragment.addspace.setOnClickListener(View.OnClickListener {
            val dialogFragment = CreateSpace()
            dialogFragment.show(parentFragmentManager, "MyDialogFragmentTag")
        })
        return bindingFragment.root
    }




    companion object {

    }


}