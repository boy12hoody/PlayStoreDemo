package uz.boywonder.playstoredemo.ui.main

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.boywonder.playstoredemo.R
import uz.boywonder.playstoredemo.databinding.ActivityMainBinding
import uz.boywonder.playstoredemo.ui.settings.SettingsViewModel
import uz.boywonder.playstoredemo.util.Constants.Companion.THEME_TYPE_DARK
import uz.boywonder.playstoredemo.util.Constants.Companion.THEME_TYPE_LIGHT
import uz.boywonder.playstoredemo.util.Constants.Companion.THEME_TYPE_SYSTEM
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(binding.toolbar)

        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingsViewModel.themeType.collect { event ->
                    applyDayNight(event)
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingsViewModel.langType.collect { event ->
                    applyLocale(event)
                }
            }
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
        Log.d("applyDayNight: ", state)
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

    private fun applyLocale(lang: String?) {
        Log.d("applyLocale: ", lang.toString())
        if (lang != null) {
            val config = resources.configuration
            val locale = Locale(lang)
            Locale.setDefault(locale)
            config.setLocale(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                createConfigurationContext(config)
            }
            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }

}