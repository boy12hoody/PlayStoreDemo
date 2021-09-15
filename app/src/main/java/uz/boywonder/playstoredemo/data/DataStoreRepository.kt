package uz.boywonder.playstoredemo.data

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import uz.boywonder.playstoredemo.util.Constants.Companion.PREFERENCES_LANG_TYPE
import uz.boywonder.playstoredemo.util.Constants.Companion.PREFERENCES_NAME
import uz.boywonder.playstoredemo.util.Constants.Companion.PREFERENCES_THEME_TYPE
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val selectedLangType = stringPreferencesKey(PREFERENCES_LANG_TYPE)
        val selectedThemeType = stringPreferencesKey(PREFERENCES_THEME_TYPE)
    }
}