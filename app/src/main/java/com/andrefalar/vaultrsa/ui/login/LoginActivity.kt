package com.andrefalar.vaultrsa.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andrefalar.vaultrsa.R
import com.andrefalar.vaultrsa.databinding.ActivityLoginBinding
import com.google.firebase.analytics.FirebaseAnalytics

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_VaultRSA)

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Analytics Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracion de Firebase Completa")
        analytics.logEvent("InitScreen", bundle)
    }
}