package com.andrefalar.vaultrsa.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.andrefalar.vaultrsa.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        val userEmail = binding.etEmail.text
        val userPassword = binding.etPassword.text

        binding.btnSingUp.setOnClickListener {
            if (userEmail.isNotEmpty() && userPassword.isNotEmpty()) {

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        userEmail.toString(),
                        userPassword.toString()
                    ).addOnCompleteListener {
                        if(it.isSuccessful){
                            goToLogin()
                        }else{
                            showAlert()
                        }
                    }
            }
        }

    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("An error occurred authenticating the user")
        builder.setPositiveButton("Ok", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun goToLogin(){
        val intent = createLoginIntent()
        startActivity(intent)
    }

    private fun createLoginIntent():Intent{
        return Intent(this,LoginActivity::class.java)
    }
}
