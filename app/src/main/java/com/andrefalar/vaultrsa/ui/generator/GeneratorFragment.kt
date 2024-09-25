package com.andrefalar.vaultrsa.ui.generator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.andrefalar.vaultrsa.databinding.FragmentGeneratorBinding

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
            binding.tvGeneratedPassword.setText(password)
        }

        // Botón para copiar la contraseña al portapapeles
        binding.btnCopy.setOnClickListener {
            val password = binding.tvGeneratedPassword.text.toString()
            if (password.isNotEmpty()) {
                copyToClipboard(password)
                Toast.makeText(requireContext(), "Contraseña copiada al portapapeles", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Genera una contraseña primero", Toast.LENGTH_SHORT).show()
            }
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

    // Lógica para copiar la contraseña al portapapeles
    private fun copyToClipboard(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Contraseña", text)
        clipboard.setPrimaryClip(clip)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
