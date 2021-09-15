package uz.boywonder.playstoredemo.ui

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import uz.boywonder.playstoredemo.R
import uz.boywonder.playstoredemo.databinding.FragmentSettingsBinding

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding: FragmentSettingsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            themeChangeBtn.setOnClickListener {  }
            langChangeBtn.setOnClickListener {  }
        }
    }
}