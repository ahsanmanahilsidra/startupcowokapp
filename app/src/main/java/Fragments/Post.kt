package Fragments

import Adopters.PostAdapter
import Models.post
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.starupcowokapp.databinding.FragmentPostBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import constants.user_post_directory


class Post : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var postList = ArrayList<post>()
        var adapter =
            PostAdapter(requireContext(), postList, requireActivity().supportFragmentManager)
        bindingFragment.recilerview.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        bindingFragment.recilerview.adapter = adapter
        Firebase.firestore.collection(user_post_directory).get().addOnSuccessListener() {
            var templist = arrayListOf<post>()
            for (i in it.documents) {
                var post: post = i.toObject<post>()!!
                templist.add(post)
            }
            templist.reverse()
            postList.addAll(templist)

            adapter.notifyDataSetChanged();
        }
    }

    private lateinit var bindingFragment: FragmentPostBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        bindingFragment = FragmentPostBinding.inflate(inflater, container, false)
        bindingFragment.addpost.setOnClickListener(View.OnClickListener {
            val dialogFragment = Addpost()
            dialogFragment.show(parentFragmentManager, "MyDialogFragmentTag")
        })


        return bindingFragment.root
    }

    companion object {

    }
}