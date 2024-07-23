package com.andrefalar.vaultrsa.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.andrefalar.vaultrsa.databinding.ActivityLogoutBinding
import com.google.firebase.auth.FirebaseAuth

class LogoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLogoutBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initListener()
        setInfo()
    }

    private fun setInfo() {
        val email = intent.getStringExtra("email")
        binding.tvEmail.text = email
    }

    private fun initListener() {
        binding.btnLogOut.setOnClickListener {
            logout()
        }
    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        val intent = createLoginIntent()
        startActivity(intent)
    }

    private fun createLoginIntent():Intent{
        return Intent(this, LoginActivity::class.java)
    }
}