package uz.boywonder.playstoredemo.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.boywonder.playstoredemo.databinding.FragmentBottomSheetThemeBinding
import uz.boywonder.playstoredemo.ui.settings.SettingsViewModel
import uz.boywonder.playstoredemo.util.Constants.Companion.THEME_TYPE_DARK
import uz.boywonder.playstoredemo.util.Constants.Companion.THEME_TYPE_LIGHT
import uz.boywonder.playstoredemo.util.Constants.Companion.THEME_TYPE_SYSTEM

class ThemeBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetThemeBinding? = null
    private val binding get() = _binding!!
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsViewModel = ViewModelProvider(requireActivity())[SettingsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetThemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val action = ThemeBottomSheetDirections.actionThemeBottomSheetToSettingsFragment()

        binding.apply {
            systemThemeTextView.setOnClickListener {
                settingsViewModel.saveThemeChoice(THEME_TYPE_SYSTEM)
                findNavController().navigate(action)
            }

            dayThemeTextView.setOnClickListener {
                settingsViewModel.saveThemeChoice(THEME_TYPE_LIGHT)
                findNavController().navigate(action)
            }

            nightThemeTextView.setOnClickListener {
                settingsViewModel.saveThemeChoice(THEME_TYPE_DARK)
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}