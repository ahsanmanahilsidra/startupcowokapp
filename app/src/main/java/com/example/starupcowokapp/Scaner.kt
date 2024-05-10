package com.example.starupcowokapp

import Models.Attandence
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.starupcowokapp.databinding.ActivityScanerBinding
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Scaner : AppCompatActivity() {
    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    private lateinit var barcodetector: BarcodeDetector
    private lateinit var cameraSource: CameraSource
    val binding by lazy {
        ActivityScanerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    private fun inibarcode() {
        barcodetector = BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()
        cameraSource = CameraSource.Builder(this, barcodetector).setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true).build()
        binding.surficeview!!.holder.addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    cameraSource.start(binding.surficeview!!.holder)
                } catch (e: IOException) {

                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int,
            ) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }

        })
        barcodetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(this@Scaner, "barcode Scaner Has been Stoped ", Toast.LENGTH_SHORT)
                    .show()
            }

                private var isDocumentCreationInProgress = false

                @RequiresApi(Build.VERSION_CODES.O)
                override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                    val barcodes = detections.detectedItems
                    if (barcodes.size() != 0) {
                        // Check if document creation process is already in progress
                        if (!isDocumentCreationInProgress) {
                            isDocumentCreationInProgress = true // Set the flag to indicate that document creation is in progress

                            val userId = barcodes.valueAt(0).displayValue

                            FirebaseFirestore.getInstance().collection("Attandence")
                                .whereEqualTo("userid", userId)
                                .whereEqualTo("date", currentDate)
                                .get()
                                .addOnSuccessListener { querySnapshot ->
                                    if (querySnapshot.isEmpty) {
                                        // No attendance document found, create a new one
                                        val currentTime = LocalTime.now()
                                        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
                                        val formattedTime = currentTime.format(formatter)
                                        val attendance = Attandence(userId, "present", currentDate,formattedTime.toString())
                                        FirebaseFirestore.getInstance().collection("Attandence")
                                            .add(attendance)
                                            .addOnSuccessListener {
                                                finish()
                                                Toast.makeText(
                                                    applicationContext,
                                                    "New attendance document created",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                vibrateDevice()

                                                // Reset the flag after document creation
                                                isDocumentCreationInProgress = false
                                            }
                                            .addOnFailureListener { exception ->
                                                Toast.makeText(
                                                    applicationContext,
                                                    "Error: ${exception.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                // Reset the flag in case of failure
                                                isDocumentCreationInProgress = false
                                            }
                                    } else {
                                        // Attendance document already exists for today
                                        Toast.makeText(
                                            applicationContext,
                                            "Attendance already marked for today",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        finish()

                                        // Reset the flag since no document creation is required
                                        isDocumentCreationInProgress = false
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(
                                        applicationContext,
                                        "Error: ${exception.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // Reset the flag in case of failure
                                    isDocumentCreationInProgress = false
                                }
                        }
                    }
                }




        })
    }

    override fun onPause() {
        super.onPause()
        cameraSource!!.release()
    }

    override fun onResume() {
        super.onResume()
        inibarcode()
    }

    private fun vibrateDevice() {
        var vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    200, // Vibration duration in milliseconds
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            // For older versions without VibrationEffect API
            @Suppress("DEPRECATION")
            vibrator.vibrate(200) // Vibrate for 200 milliseconds
        }
    }
}