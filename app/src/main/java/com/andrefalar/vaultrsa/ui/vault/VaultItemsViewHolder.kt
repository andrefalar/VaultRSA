package com.andrefalar.vaultrsa.ui.vault

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andrefalar.vaultrsa.databinding.ItemVaultBinding

class VaultItemsViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemVaultBinding.bind(view)

    fun render(vaultItem: VaultItem) {
        binding.tvSiteName.text = vaultItem.siteName
        binding.tvUsername.text = vaultItem.userName
        binding.tvPassword.text = vaultItem.password
    }
}