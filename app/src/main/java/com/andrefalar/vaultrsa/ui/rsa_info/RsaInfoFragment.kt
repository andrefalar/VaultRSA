package com.andrefalar.vaultrsa.ui.rsa_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrefalar.vaultrsa.databinding.FragmentRsaInfoBinding

class RsaInfoFragment : Fragment() {

    private var _binding: FragmentRsaInfoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRsaInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}