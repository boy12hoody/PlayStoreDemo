package uz.boywonder.playstoredemo.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import uz.boywonder.playstoredemo.util.Constants.Companion.LANG_TYPE_UZ
import uz.boywonder.playstoredemo.util.Constants.Companion.PREFERENCES_LANG_TYPE
import uz.boywonder.playstoredemo.util.Constants.Companion.PREFERENCES_NAME
import uz.boywonder.playstoredemo.util.Constants.Companion.PREFERENCES_THEME_TYPE
import uz.boywonder.playstoredemo.util.Constants.Companion.THEME_TYPE_SYSTEM
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val selectedLangType = stringPreferencesKey(PREFERENCES_LANG_TYPE)
        val selectedThemeType = stringPreferencesKey(PREFERENCES_THEME_TYPE)
    }

    /* LANGUAGE SELECTION */

    suspend fun saveLangChoice(langType: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.selectedLangType] = langType
        }
    }

    val readLangChoice: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.selectedLangType] ?: LANG_TYPE_UZ
        }


//    @TargetApi(Build.VERSION_CODES.N)
//    private fun updateResources(context: Context, language: String): Context {
//        val locale = Locale(language)
//        Locale.setDefault(locale)
//        val configuration = context.resources.configuration
//        configuration.setLocale(locale)
//        configuration.setLayoutDirection(locale)
//        return context.createConfigurationContext(configuration)
//    }
//
//    private fun updateResourcesLegacy(context: Context, language: String): Context {
//        val locale = Locale(language)
//        Locale.setDefault(locale)
//        val resources = context.resources
//        val configuration = resources.configuration
//        configuration.locale = locale
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            configuration.setLayoutDirection(locale)
//        }
//        resources.updateConfiguration(configuration, resources.displayMetrics)
//        return context
//    }

    /* THEME SELECTION */

    suspend fun saveThemeChoice(themeType: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.selectedThemeType] = themeType
        }
    }

    val readThemeChoice: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.selectedThemeType] ?: THEME_TYPE_SYSTEM
        }
}