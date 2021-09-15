package uz.boywonder.playstoredemo.ui.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.boywonder.playstoredemo.data.DataStoreRepository
import uz.boywonder.playstoredemo.util.Constants.Companion.LANG_TYPE_UZ
import uz.boywonder.playstoredemo.util.Constants.Companion.THEME_TYPE_SYSTEM
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private var langType = LANG_TYPE_UZ
    private var themeType = THEME_TYPE_SYSTEM


}