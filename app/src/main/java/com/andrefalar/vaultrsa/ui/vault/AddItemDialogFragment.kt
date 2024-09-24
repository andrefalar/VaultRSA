package com.andrefalar.vaultrsa.ui.vault

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            val site = binding.etSite.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            // Envía los datos de vuelta al fragmento de origen
            (targetFragment as VaultFragment).addItem(VaultItem(site, username, password))
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}