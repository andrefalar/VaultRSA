package com.andrefalar.vaultrsa.ui.vault

import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrefalar.vaultrsa.databinding.FragmentVaultBinding
import com.andrefalar.vaultrsa.security.RSAEncryptionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class VaultFragment : Fragment() {

    private var _binding: FragmentVaultBinding? = null
    private val binding get() = _binding!!
    private lateinit var vaultItemsAdapter: VaultItemsAdapter
    private lateinit var db: FirebaseFirestore
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    // Instancia de RSAEncryptionManager para cifrar/descifrar
    private val rsaEncryptionManager = RSAEncryptionManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVaultBinding.inflate(layoutInflater, container, false)
        db = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        loadVaultItemsFromFirestore()
    }

    private fun initUI() {
        initListeners()
        vaultItemsAdapter = VaultItemsAdapter(mutableListOf(), this)
        binding.rvItemsVault.layoutManager = LinearLayoutManager(context)
        binding.rvItemsVault.adapter = vaultItemsAdapter
    }

    private fun initListeners() {
        binding.fabAdd.setOnClickListener {
            val dialog = AddItemDialogFragment()
            dialog.setTargetFragment(this, 0)
            dialog.show(parentFragmentManager, "AddItemDialog")
        }
    }

    private fun loadVaultItemsFromFirestore() {
        userId?.let { id ->
            db.collection("vaultItems").document(id).collection("items")
                .get()
                .addOnSuccessListener { documents ->
                    val itemsList = mutableListOf<VaultItem>()
                    for (document in documents) {
                        val item = document.toObject(VaultItem::class.java)

                        // Descifrar la contraseña
                        val encryptedPassword = Base64.decode(item.password, Base64.DEFAULT)
                        val decryptedPassword = rsaEncryptionManager.decryptData(encryptedPassword)
                        item.password = decryptedPassword

                        itemsList.add(item)
                    }
                    vaultItemsAdapter.updateItems(itemsList)
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), "Error al cargar los items: ${exception.message}", Toast.LENGTH_LONG).show()
                    Log.e("VaultFragment", "Error al cargar los items desde Firestore", exception)
                }
        }
    }

    fun addItem(vaultItem: VaultItem) {
        userId?.let { id ->
            // Cifrar la contraseña antes de agregar el ítem a Firestore
            val encryptedPassword = rsaEncryptionManager.encryptData(vaultItem.password)
            val encodedPassword = Base64.encodeToString(encryptedPassword, Base64.DEFAULT)

            // Crear un nuevo VaultItem con la contraseña cifrada
            val encryptedItem = vaultItem.copy(password = encodedPassword)

            db.collection("vaultItems").document(id).collection("items")
                .add(encryptedItem)
                .addOnSuccessListener {
                    vaultItemsAdapter.addItem(vaultItem)
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), "Error al agregar el item: ${exception.message}", Toast.LENGTH_LONG).show()
                    Log.e("VaultFragment", "Error al agregar el item a Firestore", exception)
                }
        }
    }

    fun deleteItem(vaultItem: VaultItem, position: Int) {
        userId?.let { id ->

            Log.e("VaultFragment", "deleteItem called")
            db.collection("vaultItems").document(id).collection("items")
                .whereEqualTo("siteName", vaultItem.siteName)
                .whereEqualTo("userName", vaultItem.userName)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        db.collection("vaultItems").document(id).collection("items")
                            .document(document.id)
                            .delete()
                            .addOnSuccessListener {
                                vaultItemsAdapter.removeItem(position)
                                Log.e("VaultFragment", "deleteItem called")
                            }
                            .addOnFailureListener { exception ->
                                Toast.makeText(requireContext(), "Error al eliminar el item: ${exception.message}", Toast.LENGTH_LONG).show()
                                Log.e("VaultFragment", "Error al eliminar el item de Firestore", exception)
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), "Error al buscar el item para eliminar: ${exception.message}", Toast.LENGTH_LONG).show()
                    Log.e("VaultFragment", "Error al buscar el item en Firestore", exception)
                }
        }
    }
}
