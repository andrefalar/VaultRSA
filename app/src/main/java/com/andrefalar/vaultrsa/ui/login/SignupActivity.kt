package com.andrefalar.vaultrsa.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.andrefalar.vaultrsa.R
import com.andrefalar.vaultrsa.databinding.ActivitySignupBinding
import com.andrefalar.vaultrsa.ui.home.HomeActivity
import com.andrefalar.vaultrsa.ui.home.ProviderType
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        binding.btnSingUp.setOnClickListener {
            if (binding.etEmail.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()) {

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
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
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
}
