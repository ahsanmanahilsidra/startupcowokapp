package Adopters

import Fragments.Event
import Fragments.Home
import Fragments.Post
import Fragments.Space
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class Myadapter(fragmentActivity: Home):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position)
        {
            0->return Space()
            1->return Event()
            2->return Post()
            else->return Post()
        }

    }

}