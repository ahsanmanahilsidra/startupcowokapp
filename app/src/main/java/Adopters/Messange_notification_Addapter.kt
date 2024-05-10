package Adopters

import Fragments.Messages
import Fragments.Notfication_chat
import Fragments.Notifications
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class Messange_notification_Addapter(FragmentActivity:Notfication_chat):FragmentStateAdapter(FragmentActivity) {
    override fun getItemCount(): Int {
        return 2;
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0->return Notifications()
            1->return Messages()
            else->return Notifications()
        }

    }
}