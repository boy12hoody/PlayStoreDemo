package uz.boywonder.playstoredemo.ui

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import uz.boywonder.playstoredemo.R
import uz.boywonder.playstoredemo.adapter.ViewPagerAdapter
import uz.boywonder.playstoredemo.databinding.FragmentViewPagerBinding

@AndroidEntryPoint
class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {

    private val binding: FragmentViewPagerBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titles = ArrayList<String>()
        titles.add(getString(R.string.tab_normal))
        titles.add(getString(R.string.tab_list))

        val pagerAdapter = ViewPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)

        binding.apply {
            viewPager2.adapter = pagerAdapter
            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = titles[position]
            }.attach()
        }


    }
}