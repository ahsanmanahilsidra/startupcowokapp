package com.example.starupcowokapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

class payment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        val webView: WebView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true

        // Load JazzCash Payment Page
        val paymentUrl = "YOUR_PAYMENT_URL"
        webView.loadUrl(paymentUrl)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {
                // Check if the URL starts with your redirect URL
                if (url != null && url.startsWith("YOUR_REDIRECT_URL")) {
                    // Handle the payment response here
                    // Extract data from the URL if needed
                    // For example, if your redirect URL is like: YOUR_REDIRECT_URL?payment_status=success
                    // You can extract "payment_status" parameter from the URL
                    // Do something based on the payment status
                    return true // Return true to indicate this URL has been handled
                }
                return super.shouldOverrideUrlLoading(view, url)
            }
        }
    }

}