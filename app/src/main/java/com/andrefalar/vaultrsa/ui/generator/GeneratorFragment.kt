package com.andrefalar.vaultrsa.ui.generator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.andrefalar.vaultrsa.databinding.FragmentGeneratorBinding
import kotlin.random.Random

class GeneratorFragment : Fragment() {

    private var _binding: FragmentGeneratorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGeneratorBinding.inflate(layoutInflater, container, false)

        // Botón para generar la contraseña
        binding.btnGenerate.setOnClickListener {
            val password = generatePassword(12)  // Tamaño de la contraseña
            binding.txtPassword.setText(password)
        }

        return binding.root
    }

    // Lógica para generar una contraseña segura
    private fun generatePassword(length: Int): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#\$%^&*()-_=+<>?"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
