package uz.boywonder.playstoredemo.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import uz.boywonder.playstoredemo.R
import uz.boywonder.playstoredemo.adapter.ViewPagerAdapter
import uz.boywonder.playstoredemo.databinding.FragmentViewPagerBinding
import uz.boywonder.playstoredemo.ui.main.MainViewModel
import uz.boywonder.playstoredemo.util.NetworkListener

@AndroidEntryPoint
class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {

    private val binding: FragmentViewPagerBinding by viewBinding()
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var networkListener: NetworkListener

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titles = ArrayList<String>()
        titles.add(getString(R.string.tab_normal))
        titles.add(getString(R.string.tab_list))

        val pagerAdapter = ViewPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)

        binding.apply {
            viewPager2.isUserInputEnabled = false
            viewPager2.adapter = pagerAdapter
            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = titles[position]
            }.attach()
        }

        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext()).collect { status ->
                Log.d("NetworkListener", status.toString())

                when (status) {

                    true -> {
                        if (mainViewModel.isOffline) {
                            Snackbar.make(
                                requireView(), "Back Online.", Snackbar.LENGTH_SHORT
                            ).setAction("Okay") {}.show()

                            mainViewModel.isOffline = false
                        }
                    }

                    false -> {
                        Snackbar.make(
                            requireView(), "No Internet Connection.", Snackbar.LENGTH_SHORT
                        ).setAction("Okay") {}.show()

                        mainViewModel.isOffline = true
                    }
                }
            }
        }
    }
}