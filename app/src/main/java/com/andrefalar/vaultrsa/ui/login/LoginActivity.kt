package com.andrefalar.vaultrsa.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.andrefalar.vaultrsa.R
import com.andrefalar.vaultrsa.databinding.ActivityLoginBinding
import com.andrefalar.vaultrsa.ui.home.HomeActivity
import com.andrefalar.vaultrsa.ui.home.ProviderType
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        // Reset default Theme after splash screen
        setTheme(R.style.Theme_VaultRSA)

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initAnalytics()
        initListeners()

    }

    private fun initListeners() {

        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            goToHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            showAlert()
                        }
                    }
            }
        }

        binding.btnNuSignUp.setOnClickListener {
            goToSingUp()
        }
    }

    private fun initAnalytics() {
        // Analytics Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracion de Firebase Completa")
        analytics.logEvent("InitScreen", bundle)
    }

    private fun goToSingUp() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }

    private fun goToHome(email:String, provider: ProviderType){
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(intent)
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