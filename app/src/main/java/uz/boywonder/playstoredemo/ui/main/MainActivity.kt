package uz.boywonder.playstoredemo.ui.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.boywonder.playstoredemo.R
import uz.boywonder.playstoredemo.databinding.ActivityMainBinding
import uz.boywonder.playstoredemo.ui.settings.SettingsViewModel
import uz.boywonder.playstoredemo.util.Constants.Companion.THEME_TYPE_DARK
import uz.boywonder.playstoredemo.util.Constants.Companion.THEME_TYPE_LIGHT
import uz.boywonder.playstoredemo.util.Constants.Companion.THEME_TYPE_SYSTEM


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

    override fun recreate() {
        super.recreate()
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim)
    }
}