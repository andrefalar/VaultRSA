package com.andrefalar.vaultrsa.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.andrefalar.vaultrsa.R
import com.andrefalar.vaultrsa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initNavigation()

    }

    private fun initNavigation() {
        setSupportActionBar(binding.toolbar)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = destination.label
        }
    }
}