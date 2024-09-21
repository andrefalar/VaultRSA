package com.andrefalar.vaultrsa.ui.vault

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrefalar.vaultrsa.R

class VaultItemsAdapter (private val vaultItemsList: List<VaultItem>) : RecyclerView.Adapter<VaultItemsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaultItemsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vault, parent, false)
        return VaultItemsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vaultItemsList.size
    }

    override fun onBindViewHolder(holder: VaultItemsViewHolder, position: Int) {
        holder.render(vaultItemsList[position])
    }
}