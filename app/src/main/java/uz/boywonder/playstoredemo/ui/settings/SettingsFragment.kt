package uz.boywonder.playstoredemo.ui.settings

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.boywonder.playstoredemo.R
import uz.boywonder.playstoredemo.databinding.FragmentSettingsBinding
import uz.boywonder.playstoredemo.util.navigateSafe

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding: FragmentSettingsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            themeChangeBtn.setOnClickListener {
                val action = SettingsFragmentDirections.actionSettingsFragmentToThemeBottomSheet()
//                findNavController().navigate(action) crashes because Google
                findNavController().navigateSafe(action)
            }
            langChangeBtn.setOnClickListener {
                val action =
                    SettingsFragmentDirections.actionSettingsFragmentToLanguageBottomSheet()
//                findNavController().navigate(action) crashes because Google
                findNavController().navigateSafe(action)
            }
        }
    }
}