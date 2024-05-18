package com.example.starupcowokapp

import DeleteExpiredDocumentsWorker
import Fragments.Notfication_chat
import Fragments.profile
import Fragments.Community
import Fragments.Home
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.starupcowokapp.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.util.Calendar
import java.util.concurrent.TimeUnit

class Home : AppCompatActivity() {
    val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        replacefragment(Fragments.Home())
        scheduleDeleteExpiredDocumentsTask()
        binding.bottombar.setOnItemSelectedListener {


            when (it.itemId) {
                R.id.space -> replacefragment(Home())
                R.id.Community -> replacefragment(Community())
                R.id.notification -> replacefragment(Notfication_chat())
                R.id.profile -> replacefragment(profile())
                else

                -> {

                }
            }
            true
        }

    }


    fun replacefragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment)
            .addToBackStack(null).commit()
    }
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        } else {
            showExitConfirmationDialog()
        }
    }


    private fun showExitConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Are you sure you want to exit?")
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setPositiveButton("Yes") { dialog, _ ->
            finishAffinity() // Close the app
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss() // Dismiss the dialog
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    private fun setBadgeCount(count: Int, bottomNavigationView: BottomNavigationView) {
        // Get the menu item you want to add a badge to
        val menuItem = bottomNavigationView.menu.findItem(R.id.notification)

        // Create a BadgeDrawable
        val badgeDrawable = bottomNavigationView.getOrCreateBadge(menuItem.itemId)

        // Set badge count
        badgeDrawable.number = count
    }
    private fun scheduleDeleteExpiredDocumentsTask() {
        // Define the time at which you want the task to run every day (in this example, 3:00 AM)
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 18)
            set(Calendar.MINUTE, 43)
            set(Calendar.SECOND, 0)
        }

        // Create a periodic work request to run the task daily
        val deleteExpiredDocumentsRequest =
            PeriodicWorkRequestBuilder<DeleteExpiredDocumentsWorker>(
                1,
                TimeUnit.DAYS
            )
                .setInitialDelay(
                    calendar.timeInMillis - System.currentTimeMillis(),
                    TimeUnit.MILLISECONDS
                )
                .build()

        // Schedule the periodic work request
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "deleteExpiredDocumentsTask",
                ExistingPeriodicWorkPolicy.REPLACE,
                deleteExpiredDocumentsRequest
            )
    }
}
