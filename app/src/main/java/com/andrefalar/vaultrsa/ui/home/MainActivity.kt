package com.andrefalar.vaultrsa.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andrefalar.vaultrsa.R
import com.andrefalar.vaultrsa.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}