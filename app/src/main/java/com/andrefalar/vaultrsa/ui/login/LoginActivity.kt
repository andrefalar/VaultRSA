package com.andrefalar.vaultrsa.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.andrefalar.vaultrsa.R
import com.andrefalar.vaultrsa.databinding.ActivityLoginBinding
import com.andrefalar.vaultrsa.ui.home.HomeActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        // Reset default Theme after splash screen
        setTheme(R.style.Theme_VaultRSA)

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        // Default Dark Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initAnalytics()
        initListeners()

    }

    private fun initListeners() {

        val userEmail = binding.etEmail.text
        val userPassword = binding.etPassword.text

        binding.btnLogin.setOnClickListener {
            if (userEmail.isNotEmpty() && userPassword.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        userEmail.toString(),
                        userPassword.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            goToHome(it.result?.user?.email ?: "")
                        } else {
                            showAlert()
                        }
                    }
            }
        }

        binding.btnNuSignUp.setOnClickListener {
            goToSignUp()
        }
    }

    private fun initAnalytics() {
        // Analytics Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracion de Firebase Completa")
        analytics.logEvent("InitScreen", bundle)
    }

    private fun goToSignUp() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }

    private fun goToHome(email: String) {
        val intent = createLogoutIntent(email)
        startActivity(intent)
    }

    private fun createLogoutIntent(email: String?): Intent {
        return Intent(this,HomeActivity::class.java).apply {
            putExtra("email", email)
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("The entered user does not exist")
        builder.setPositiveButton("Ok", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}