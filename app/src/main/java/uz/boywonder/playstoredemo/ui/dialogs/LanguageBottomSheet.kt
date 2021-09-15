package uz.boywonder.playstoredemo.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.boywonder.playstoredemo.databinding.FragmentBottomSheetLanguageBinding
import uz.boywonder.playstoredemo.ui.settings.SettingsViewModel

class LanguageBottomSheet : BottomSheetDialogFragment() {

    private val binding: FragmentBottomSheetLanguageBinding by viewBinding()
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsViewModel = ViewModelProvider(requireActivity())[SettingsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}