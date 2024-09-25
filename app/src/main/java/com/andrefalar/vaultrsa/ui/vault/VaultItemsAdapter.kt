package com.andrefalar.vaultrsa.ui.vault

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrefalar.vaultrsa.R

class VaultItemsAdapter(
    private val vaultItemsList: MutableList<VaultItem> = mutableListOf(),
    private val vaultFragment: VaultFragment
) : RecyclerView.Adapter<VaultItemsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaultItemsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vault, parent, false)
        return VaultItemsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vaultItemsList.size
    }

    override fun onBindViewHolder(holder: VaultItemsViewHolder, position: Int) {
        holder.render(vaultItemsList[position]) { positionToDelete ->
            vaultFragment.deleteItem(vaultItemsList[positionToDelete], positionToDelete) // Usar la referencia correcta
        }
    }

    fun addItem(item: VaultItem) {
        vaultItemsList.add(item)
        notifyItemInserted(vaultItemsList.size - 1)
    }

    fun removeItem(position: Int) {
        if (position >= 0 && position < vaultItemsList.size) {
            vaultItemsList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, vaultItemsList.size)
        }
    }

    fun updateItems(items: List<VaultItem>) {
        vaultItemsList.clear()
        vaultItemsList.addAll(items)
        notifyDataSetChanged()
    }
}