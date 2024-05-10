package Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.FragmentChangeEmailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class Change_email : BottomSheetDialogFragment() {
    private lateinit var bindingFragment: FragmentChangeEmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        bindingFragment = FragmentChangeEmailBinding.inflate(layoutInflater, container, false)

        bindingFragment.changebtn.setOnClickListener(View.OnClickListener {

            FirebaseAuth.getInstance().currentUser?.updateEmail(bindingFragment.email.text.toString())
                ?.addOnSuccessListener {
                    Toast.makeText(context,"Email Update Successfully", Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                }?.addOnFailureListener { exception ->

                    when (exception) {
                        is FirebaseAuthInvalidUserException -> {
                            Toast.makeText(context,"User is no longer valid, consider re-authenticating the user",
                                Toast.LENGTH_SHORT).show()
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            Toast.makeText(context,"Invalid email address", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(context," Other error", Toast.LENGTH_SHORT).show()

                        }
                    }
                }

        })
        bindingFragment.cancelbtn.setOnClickListener(View.OnClickListener {
            dialog?.dismiss()
        })

        return  bindingFragment.root
    }


}