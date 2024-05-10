package Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.FragmentChangePasswordBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class Change_password : BottomSheetDialogFragment(){
    private lateinit var bindingFragment: FragmentChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingFragment = FragmentChangePasswordBinding.inflate(layoutInflater, container, false)
        bindingFragment.change.setOnClickListener(View.OnClickListener {
            FirebaseAuth.getInstance().currentUser?.updatePassword(bindingFragment.confirmPassword.text.toString())
                ?.addOnSuccessListener {
                    Toast.makeText(context,"Password updated successfully", Toast.LENGTH_SHORT).show()
                }?.addOnFailureListener { exception ->
                    when (exception) {
                        is FirebaseAuthInvalidUserException -> {
                            Toast.makeText(context,"User is no longer valid, consider re-authenticating the user",
                                Toast.LENGTH_SHORT).show()
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            Toast.makeText(context,"Invalid password", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(context,"Other erro", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

        })
bindingFragment.cancelButton.setOnClickListener(View.OnClickListener {
    dialog?.dismiss()
})
        return bindingFragment.root
    }


}
