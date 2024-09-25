package com.andrefalar.vaultrsa.ui.vault

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.andrefalar.vaultrsa.databinding.DialogAddItemBinding

class AddItemDialogFragment : DialogFragment() {

    private lateinit var binding: DialogAddItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla el layout del diálogo
        binding = DialogAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            val site = binding.etSite.text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (site.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                // Envía los datos al fragmento de origen (VaultFragment)
                (targetFragment as VaultFragment).addItem(VaultItem(site, username, password))
                dismiss() // Cierra el diálogo después de añadir el item
            }
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}