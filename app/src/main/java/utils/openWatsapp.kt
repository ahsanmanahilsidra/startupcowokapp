package utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openWhatsAppChat(context: Context, phoneNumber: String) {
    // Format the phone number properly for WhatsApp
    val formattedPhoneNumber = "+$phoneNumber"

    // Create a Uri for the phone number
    val uri = Uri.parse("https://wa.me/$formattedPhoneNumber")

    // Create an intent with the ACTION_VIEW action and the Uri
    val intent = Intent(Intent.ACTION_VIEW, uri)

    // Check if WhatsApp is installed on the device

        // Start the intent to open WhatsApp
        context.startActivity(intent)

}