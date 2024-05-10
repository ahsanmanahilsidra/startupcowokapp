package Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.FragmentShowimgBinding
import com.example.starupcowokapp.databinding.FragmentShowqrcodeBinding
import com.squareup.picasso.Picasso


class showimg : DialogFragment() {
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
    private lateinit var bindingFragment:FragmentShowimgBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val posturl = arguments?.getString("posturl")
        bindingFragment = FragmentShowimgBinding.inflate(layoutInflater, container, false)
        Picasso.get().load(posturl).placeholder(R.drawable.loading).into(bindingFragment.showimg)

        return bindingFragment.root

    }

    companion object {
        fun newInstance(posturl: String): showimg {
            val fragment = showimg()
            val args = Bundle()
            args.putString("posturl", posturl)
            fragment.arguments = args
            return fragment
        }

    }
}