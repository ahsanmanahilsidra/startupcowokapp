package Fragments

import Adopters.Messange_notification_Addapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.starupcowokapp.R

import com.example.starupcowokapp.databinding.FragmentNotificationBinding
import com.google.android.material.tabs.TabLayoutMediator

class Notfication_chat : Fragment() {

    private lateinit var bindingFragment: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingFragment = FragmentNotificationBinding.inflate(inflater, container, false)
        bindingFragment.viewPager2.adapter =Messange_notification_Addapter(this  )
        TabLayoutMediator(bindingFragment.Tablayout, bindingFragment.viewPager2) { tab, Position ->
            when (Position) {
                0 -> {
                    tab.text="Notifications"

                }
                1 -> {tab.text = "Messages"
                }

            }
        }.attach()
        bindingFragment.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Here you can handle the selected fragment position
                when (position) {
                    0 -> {
                        bindingFragment.title.setText("Notification")
                    }
                    1 -> {
                        bindingFragment.title.setText("Chat")
                    }

                    // Add more cases as needed
                }
            }
        })

        return bindingFragment.root
    }

    companion object {


    }
}