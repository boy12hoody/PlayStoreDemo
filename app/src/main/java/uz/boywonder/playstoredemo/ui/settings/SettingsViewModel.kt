package uz.boywonder.playstoredemo.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.boywonder.playstoredemo.data.DataStoreRepository
import uz.boywonder.playstoredemo.util.Constants.Companion.LANG_TYPE_UZ
import uz.boywonder.playstoredemo.util.Constants.Companion.THEME_TYPE_SYSTEM
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private var _langType: MutableLiveData<String> = MutableLiveData(LANG_TYPE_UZ)
    val langType: LiveData<String> get() = _langType

    private var _themeType: MutableLiveData<String> = MutableLiveData(THEME_TYPE_SYSTEM)
    val themeType: LiveData<String> get() = _themeType

    // Since manual language selection is not working for me, it's disabled temporarily.
    private val readLangChoice = dataStoreRepository.readLangChoice
    fun readLangChoice() {
        viewModelScope.launch {
            readLangChoice.collect { value ->
                _langType.value = value
            }
        }
    }

    private val readThemeChoice = dataStoreRepository.readThemeChoice
    fun readThemeChoice() {
        viewModelScope.launch {
            readThemeChoice.collect { value ->
                _themeType.value = value
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