package com.andrefalar.vaultrsa.ui.vault

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.andrefalar.vaultrsa.databinding.ItemVaultBinding

class VaultItemsViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemVaultBinding.bind(view)

    fun render(vaultItem: VaultItem, onDelete: (Int) -> Unit) {
        binding.tvSiteName.text = vaultItem.siteName
        binding.tvUsername.text = vaultItem.userName
        binding.tvPassword.text = vaultItem.password

        // Listener para copiar el nombre de usuario
        binding.ivCopyUsername.setOnClickListener {
            copyToClipboard(it.context, "Usuario", vaultItem.userName)
        }

        // Listener para copiar la contraseña
        binding.ivCopyPassword.setOnClickListener {
            copyToClipboard(it.context, "Contraseña", vaultItem.password)
        }

        // Listener para eliminar el ítem
        binding.ivDeleteItem.setOnClickListener {
            onDelete(adapterPosition)
            Toast.makeText(it.context, "Item eliminado", Toast.LENGTH_SHORT).show()
            Log.e("Listener", "good")
        }
    }

    private fun copyToClipboard(context: Context, label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "$label copiado al portapapeles", Toast.LENGTH_SHORT).show()
    }
}