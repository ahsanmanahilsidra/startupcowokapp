package com.example.starupcowokapp

import DeleteExpiredDocumentsWorker
import Fragments.Community
import Fragments.Home
import Fragments.Notfication_chat
import Fragments.profile
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.starupcowokapp.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import markapsent
import java.util.*
import java.util.concurrent.TimeUnit

class Home : AppCompatActivity() {
    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        replaceFragment(Home())
        schedulePeriodicTasks()
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        binding.bottombar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.space -> replaceFragment(Home())
                R.id.Community -> replaceFragment(Community())
                R.id.notification -> replaceFragment(Notfication_chat())
                R.id.profile -> replaceFragment(profile())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        } else {
            showExitConfirmationDialog()
        }
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to exit?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                finishAffinity() // Close the app
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss() // Dismiss the dialog
            }
            .create()
            .show()
    }

    private fun schedulePeriodicTasks() {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 19) // Set the time to 7:00 PM for both tasks
            set(Calendar.MINUTE, 49)
            set(Calendar.SECOND, 0)
        }

        val deleteExpiredDocumentsRequest = PeriodicWorkRequestBuilder<DeleteExpiredDocumentsWorker>(
            1, TimeUnit.DAYS
        ).setInitialDelay(
            calendar.timeInMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS
        ).build()

        val markAttendanceRequest = PeriodicWorkRequestBuilder<markapsent>(
            1, TimeUnit.DAYS
        ).setInitialDelay(
            calendar.timeInMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "deleteExpiredDocumentsTask", ExistingPeriodicWorkPolicy.REPLACE, deleteExpiredDocumentsRequest
        )

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "markAttendanceTask", ExistingPeriodicWorkPolicy.REPLACE, markAttendanceRequest
        )
    }
}
