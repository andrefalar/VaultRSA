package com.andrefalar.vaultrsa.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.andrefalar.vaultrsa.R
import com.andrefalar.vaultrsa.databinding.ActivityHomeBinding
import com.andrefalar.vaultrsa.ui.login.LogoutActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.HomeTheme)

        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(binding.root)
        initUI()
    }


    private fun initUI() {
        initNavigation()
        initToolbar()
        initListeners()
    }

    private fun initListeners() {
        binding.ivAccount.setOnClickListener {
            goToLogout()
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        // Delete the Toolbar default title
        supportActionBar?.setDisplayShowTitleEnabled(false)
        // Change Toolbar title according the fragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.title = destination.label
        }
    }

    private fun initNavigation() {

        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavigation.setupWithNavController(navController)
    }


    private fun goToLogout() {
        val email = intent.getStringExtra("email")
        val intent = createLogoutIntent(email)
        startActivity(intent)
    }

    private fun createLogoutIntent(email: String?): Intent {
        return Intent(this,LogoutActivity::class.java).apply {
            putExtra("email", email)
        }
    }
}