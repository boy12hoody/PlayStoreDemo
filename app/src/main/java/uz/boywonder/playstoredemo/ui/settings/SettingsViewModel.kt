package uz.boywonder.playstoredemo.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.boywonder.playstoredemo.data.DataStoreRepository
import uz.boywonder.playstoredemo.util.Constants.Companion.LANG_TYPE_UZ
import uz.boywonder.playstoredemo.util.Constants.Companion.THEME_TYPE_SYSTEM
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private var _langType: MutableStateFlow<String> = MutableStateFlow(LANG_TYPE_UZ)
    val langType: StateFlow<String> = _langType

    private var _themeType: MutableStateFlow<String> = MutableStateFlow(THEME_TYPE_SYSTEM)
    val themeType: StateFlow<String> = _themeType

    // Since manual language selection is not working for me, it's disabled temporarily.
    private val readLangChoice = dataStoreRepository.readLangChoice
    fun readLangChoice() {
        viewModelScope.launch {
            readLangChoice.collect { lang ->
                _langType.value = lang
            }
        }
    }

    private val readThemeChoice = dataStoreRepository.readThemeChoice
    fun readThemeChoice() {
        viewModelScope.launch {
            readThemeChoice.collect { theme ->
                _themeType.value = theme
            }
        }
    }

    fun saveLangChoice(langType: String) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveLangChoice(langType)
        }

    fun saveThemeChoice(themeType: String) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveThemeChoice(themeType)
        }
}