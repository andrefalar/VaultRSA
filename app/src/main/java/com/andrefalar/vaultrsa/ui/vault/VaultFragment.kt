package com.andrefalar.vaultrsa.ui.vault

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrefalar.vaultrsa.R
import com.andrefalar.vaultrsa.databinding.FragmentVaultBinding

class VaultFragment : Fragment() {

    private var _binding: FragmentVaultBinding? = null
    private val binding get() = _binding
    private lateinit var vaultItemsAdapter: VaultItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVaultBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

    }

    private fun initUI() {
        val vaultItemsList = mutableListOf(
            VaultItem("Facebook", "andrefalar", "12345"),
            VaultItem("Google", "andrefalar", "67891"),
            VaultItem("Instagram", "andrefalar", "894894")
        )
        vaultItemsAdapter = VaultItemsAdapter(vaultItemsList)
        binding?.rvItemsVault?.layoutManager = LinearLayoutManager(context)
        binding?.rvItemsVault?.adapter = vaultItemsAdapter

    }

}