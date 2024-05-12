package com.example.starupcowokapp
import Models.user
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.starupcowokapp.databinding.ActivitySignupBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import constants.user_node
import constants.user_post_directory
import constants.user_profile_directory
import uploadImage
import java.io.ByteArrayOutputStream
import java.util.Calendar
import java.util.TimeZone

class Signup : AppCompatActivity() {
    val binding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    var user = user();

    var imgulr: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.image.setImageResource(R.drawable.loading)
            uploadImage(this,uri, user_post_directory) {

                if (it != null) {
                    imgulr = it.toString()
                    Picasso.get().load(imgulr).placeholder(R.drawable.loading).into(binding.image)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.loginTextview.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, Login::class.java))

        })
        val extraValue = intent.getStringExtra("message")
        if (extraValue =="Editprofile") {
           binding.title.setText("Edit Profile")
            binding.signupButton.setText("Update Profile")
            binding.email.visibility=View.GONE
            binding.password.visibility=View.GONE
            binding.confirmPassword.visibility=View.GONE
            FirebaseFirestore.getInstance().collection("user").document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                binding.Username.setText( it.data!!["name"].toString())
                binding.phoneNumber.setText( it.data!!["phone_number"].toString())
                binding.dob.setText( it.data!!["date_of_birth"].toString())
                if(it.data!!["image"]!=null){
                    Picasso.get().load(it.data!!["image"].toString()).into(binding.image)
                }

            }
        }
        var selectedGender: String = ""
       binding.radioGroupGender.setOnCheckedChangeListener { _, checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)
               selectedGender = radioButton.text.toString()
            // You can perform any action with the selected gender here
        }

        binding.signupButton.setOnClickListener(/* l = */ View.OnClickListener {
            if(extraValue=="Editprofile") {
                if (binding.layoutUsername.editText?.text.toString()
                        .equals("")
                ) {
                    Toast.makeText(this, "Please Enter username", Toast.LENGTH_SHORT)
                        .show();
                } else if (binding.layoutUsername.editText?.text?.length!! > 20) {
                    Toast.makeText(this, "Username is to large ", Toast.LENGTH_SHORT)
                        .show();
                } else if (selectedGender.toString().equals("")) {
                    Toast.makeText(this, "Select the gender", Toast.LENGTH_SHORT)
                        .show();
                } else if (binding.layoutDOB.editText?.text.toString().equals("")) {
                    Toast.makeText(this, "Please Enter date of birth", Toast.LENGTH_SHORT)
                        .show();
                } else if (binding.phoneNumber.text.toString().equals("")) {
                    Toast.makeText(this, "Please Enter phone number", Toast.LENGTH_SHORT)
                        .show();

                } else if (binding.phoneNumber.text.toString().length != 11) {
                    Toast.makeText(this, "Phone number is not valid ", Toast.LENGTH_SHORT)
                        .show();

                } else {

                    val updates = hashMapOf<String, Any>(
                        "name" to binding.Username.text.toString(),
                        "phone_number" to binding.phoneNumber.text.toString(),
                        "date_of_birth" to binding.dob.text.toString(),
                        "gender" to selectedGender.toString(),
                        "image" to imgulr.toString(),
                    )
                    FirebaseFirestore.getInstance().collection("user").document(Firebase.auth.currentUser!!.uid).update(updates)
                        .addOnCompleteListener {
                            startActivity(Intent(this, Home()::class.java))
                            finish()
                            Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT)
                                .show();


                        }
                }
            }

            else{
            if (binding.layoutUsername.editText?.text.toString()
                    .equals("")) {
                Toast.makeText(this, "Please Enter username", Toast.LENGTH_SHORT)
                    .show();
                    }

           else  if (binding.layoutUsername.editText?.text?.length!! >20) {
                Toast.makeText(this, "Username is to large ", Toast.LENGTH_SHORT)
                    .show();
            }

               else  if( binding.email.text.toString()
                    .equals("")) {
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT)
                    .show();
                }
                   else if(binding.password.text.toString()
                        .equals("") ) {
                Toast.makeText(this, "Please Enter Passwword", Toast.LENGTH_SHORT)
                    .show();
                    }
            else if( binding.layoutConfirmPassword.editText?.text.toString()
                        .equals("") ) {
                Toast.makeText(this, "Please enter confirm password", Toast.LENGTH_SHORT)
                    .show();
        }
           else if (!hasSpecialCharacter(binding.password.text.toString())) {
                Toast.makeText(this, "Please enter atleast one special character", Toast.LENGTH_SHORT)
                    .show();
            }
            else if (binding.password.text.toString()!=binding.confirmPassword.text.toString()){
                Toast.makeText(this, "Password and confirm password don't match", Toast.LENGTH_SHORT)
                    .show();
            }
            else if( selectedGender.toString().equals("")){
                Toast.makeText(this, "Select the gender", Toast.LENGTH_SHORT)
                    .show();
                }

               else  if ( binding.layoutDOB.editText?.text.toString().equals("")){
                Toast.makeText(this, "Please Enter date of birth", Toast.LENGTH_SHORT)
                    .show();
                }
                else if( binding.phoneNumber.text.toString().equals(""))
             {
                Toast.makeText(this, "Please Enter phone number", Toast.LENGTH_SHORT)
                    .show();

            }
            else if( binding.phoneNumber.text.toString().length!=11)
            {
                Toast.makeText(this, "Phone number is not valid ", Toast.LENGTH_SHORT)
                    .show();

            }

            else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.text.toString(), binding.password.text.toString()
                ).addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        user.Name = binding.layoutUsername.editText?.text.toString()
                        user.Email = binding.email.text.toString()
                        user.Password = binding.password.text.toString()
                        user.Gender = selectedGender.toString()
                        user.Date_of_birth = binding.dob.text.toString()
                        user.Phone_number= binding.phoneNumber.text.toString()
                        user.Image=imgulr.toString()
                        user.role="User"
                        user.userid=Firebase.auth.currentUser!!.uid
                        Firebase.firestore.collection(user_node)
                            .document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnCompleteListener {
                                startActivity(Intent(this, Home()::class.java))
                                finish()
                                    Toast.makeText(this, "Signup Successfully", Toast.LENGTH_SHORT)
                                        .show();

                            }
                    } else {
                        Toast.makeText(this, result.exception?.localizedMessage, Toast.LENGTH_SHORT)
                            .show();
                    }
                }

            }}
        })
            val builder = MaterialDatePicker.Builder.datePicker()
            builder.setTitleText("Select Date")
//            builder.setTheme(R.style.CalendarThemeOverlayOrange)

            val materialDatePicker = builder.build()
            val selectedDateStr=""
            binding.dob.setOnClickListener {
                materialDatePicker.show(supportFragmentManager, "DATE_PICKER")
            }

            materialDatePicker.addOnPositiveButtonClickListener { selectedDate ->
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.timeInMillis = selectedDate
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH) + 1
                val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
                val selectedDateStr = "$dayOfMonth/$month/$year"
                 binding.dob.setText(selectedDateStr)
            }

        binding.addimg.setOnClickListener() {
            launcher.launch("image/*")
        }
    }
    fun hasSpecialCharacter(password: String): Boolean {
        val pattern = "[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]".toRegex()
        return pattern.containsMatchIn(password)
    }





}