package uz.boywonder.playstoredemo.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    val readLangChoice = dataStoreRepository.readLangChoice
    val readThemeChoice = dataStoreRepository.readThemeChoice

    fun saveLangChoice(langType: String) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveLangChoice(langType)
        }

    fun saveThemeChoice(themeType: String) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveThemeChoice(themeType)
        }
}