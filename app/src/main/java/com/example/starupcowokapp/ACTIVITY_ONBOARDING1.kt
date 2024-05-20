package com.example.starupcowokapp

import Adapters.OnboardingItemAdapter
import Adopters.OnboardingItem
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton

class ACTIVITY_ONBOARDING1 : AppCompatActivity() {

    private lateinit var indicatorsContainer: LinearLayout
    private lateinit var onboardingItemAdapter: OnboardingItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding1)
        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)
    }

    private fun setOnboardingItems() {
        onboardingItemAdapter = OnboardingItemAdapter(
            listOf(
                OnboardingItem(
                    onboardingImage = R.drawable.loading,
                    title = "Account Management made easy",
                    description = "Manage your account and bookings all in one place"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.loading,
                    title = "Trouble-free support",
                    description = "Experience effortless support at out coworking space, where our team is always ready to assist you."
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.loading,
                    title = "Uncover and Reserve Your Dream Workspace",
                    description = "Discover your preferred workspace and book it effortlessly.Our team is here to help!"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.loading,
                    title = "Check-In with Ease",
                    description = "Mark Your Attendance and let Us Know You're Here!"
                )
            )
        )
        val onboardingViewPager = findViewById<ViewPager2>(R.id.onboarding_viewpager)
        onboardingViewPager.adapter = onboardingItemAdapter
        onboardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
        findViewById<ImageView>(R.id.imgNext).setOnClickListener {
            if (onboardingViewPager.currentItem + 1 < onboardingItemAdapter.itemCount) {
                onboardingViewPager.currentItem += 1
            } else {
                navigateToLoginActivity()
            }
        }
        findViewById<TextView>(R.id.textSkip).setOnClickListener {
            navigateToLoginActivity()
        }
        findViewById<MaterialButton>(R.id.GetStarted).setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }

    private fun navigateToLoginActivity() {
        startActivity(Intent(applicationContext, Login::class.java))
        finish()
    }

    private fun setupIndicators() {
        indicatorsContainer = findViewById(R.id.indicatorsContainer)
        val indicators = arrayOfNulls<ImageView>(onboardingItemAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.loading
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    if (i == position) R.drawable.background_input else R.drawable.background_input
                )
            )
        }
    }
}
