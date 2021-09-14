package uz.boywonder.playstoredemo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.boywonder.playstoredemo.ui.tabs.ListTabFragment
import uz.boywonder.playstoredemo.ui.tabs.NormalTabFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> ListTabFragment()
            else -> NormalTabFragment()
        }
    }

}