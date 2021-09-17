package uz.boywonder.playstoredemo.ui.main

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate
import com.akexorcist.localizationactivity.core.OnLocaleChangedListener
import dagger.hilt.android.AndroidEntryPoint
import uz.boywonder.playstoredemo.R
import uz.boywonder.playstoredemo.databinding.ActivityMainBinding
import uz.boywonder.playstoredemo.ui.settings.SettingsViewModel
import uz.boywonder.playstoredemo.util.Constants.Companion.THEME_TYPE_DARK
import uz.boywonder.playstoredemo.util.Constants.Companion.THEME_TYPE_LIGHT
import uz.boywonder.playstoredemo.util.Constants.Companion.THEME_TYPE_SYSTEM
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnLocaleChangedListener {

    private val localizationDelegate = LocalizationActivityDelegate(this)

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        localizationDelegate.addOnLocaleChangedListener(this)
        localizationDelegate.onCreate()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(binding.toolbar)

        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController)

        settingsViewModel.readLangChoice()
        settingsViewModel.langType.observe(this) { lang ->
            setLanguage(lang)
        }

        settingsViewModel.readThemeChoice()
        settingsViewModel.themeType.observe(this) { theme ->
            applyDayNight(theme)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> applyDayNight(THEME_TYPE_LIGHT)
            Configuration.UI_MODE_NIGHT_YES -> applyDayNight(THEME_TYPE_DARK)
            Configuration.UI_MODE_NIGHT_UNDEFINED -> applyDayNight(THEME_TYPE_SYSTEM)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /* MANUAL THEME CHANGE */

    private fun applyDayNight(state: String) {
        when (state) {
            THEME_TYPE_LIGHT -> {
                AppCompatDelegate
                    .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            THEME_TYPE_DARK -> {
                AppCompatDelegate
                    .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            THEME_TYPE_SYSTEM -> {
                AppCompatDelegate
                    .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }

    /* MANUAL LANGUAGE CHANGE */

    public override fun onResume() {
        super.onResume()
        localizationDelegate.onResume(this)
    }

    override fun attachBaseContext(newBase: Context) {
        applyOverrideConfiguration(localizationDelegate.updateConfigurationLocale(newBase))
        super.attachBaseContext(newBase)
    }

    override fun getApplicationContext(): Context {
        return localizationDelegate.getApplicationContext(super.getApplicationContext())
    }

    override fun getResources(): Resources {
        return localizationDelegate.getResources(super.getResources())
    }

    private fun setLanguage(language: String?) {
        localizationDelegate.setLanguage(this, language!!)
    }

    val currentLanguage: Locale
        get() = localizationDelegate.getLanguage(this)

    override fun onAfterLocaleChanged() {}

    override fun onBeforeLocaleChanged() {}
}