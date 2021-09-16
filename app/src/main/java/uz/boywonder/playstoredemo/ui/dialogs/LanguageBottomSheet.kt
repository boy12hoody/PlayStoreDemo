package uz.boywonder.playstoredemo.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.boywonder.playstoredemo.R
import uz.boywonder.playstoredemo.databinding.FragmentBottomSheetLanguageBinding
import uz.boywonder.playstoredemo.ui.settings.SettingsViewModel
import uz.boywonder.playstoredemo.util.Constants.Companion.LANG_TYPE_EN
import uz.boywonder.playstoredemo.util.Constants.Companion.LANG_TYPE_UZ

class LanguageBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetLanguageBinding? = null
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
        _binding = FragmentBottomSheetLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val action = LanguageBottomSheetDirections.actionLanguageBottomSheetToSettingsFragment()

        binding.apply {
            uzLanguageTextView.setOnClickListener {
                settingsViewModel.saveLangChoice(LANG_TYPE_UZ)
                Toast.makeText(context, getString(R.string.lang_selection_error), Toast.LENGTH_SHORT).show()
                findNavController().navigate(action)
            }
            engLanguageTextView.setOnClickListener {
                settingsViewModel.saveLangChoice(LANG_TYPE_EN)
                Toast.makeText(context, getString(R.string.lang_selection_error), Toast.LENGTH_SHORT).show()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}