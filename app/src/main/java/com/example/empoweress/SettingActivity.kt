package com.example.empoweress

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.empoweress.databinding.ActivitySettingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SettingActivity : AppCompatActivity(), ContactAdapter.OnContactClickListener {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var contacts: MutableList<String>
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("trusted_contacts", MODE_PRIVATE)

        // Load saved contacts
        loadContacts()

        // Setup RecyclerView
        contactAdapter = ContactAdapter(contacts)
        contactAdapter.listener = this
        binding.rvContacts.apply {
            layoutManager = LinearLayoutManager(this@SettingActivity)
            adapter = contactAdapter
        }

        // Setup Add button
        binding.btnAddContact.setOnClickListener {
            addContact()
        }
    }

    private fun loadContacts() {
        val contactsJson = sharedPreferences.getString("contacts", "[]")
        val type = object : TypeToken<MutableList<String>>() {}.type
        contacts = Gson().fromJson(contactsJson, type)
    }

    private fun saveContacts() {
        val contactsJson = Gson().toJson(contacts)
        sharedPreferences.edit().putString("contacts", contactsJson).apply()
    }

    private fun addContact() {
        val phone = binding.etContact.text.toString().trim()

        if (phone.isEmpty()) {
            binding.tilContact.error = "Please enter a phone number"
            return
        }

        if (contacts.contains(phone)) {
            binding.tilContact.error = "Contact already added"
            return
        }

        contacts.add(phone)
        contactAdapter.notifyItemInserted(contacts.size - 1)
        saveContacts()

        // Clear input
        binding.etContact.text?.clear()
        binding.tilContact.error = null

        // Show confirmation
        Snackbar.make(binding.root, "Contact added successfully", Snackbar.LENGTH_SHORT).show()
    }

    override fun onDeleteClick(position: Int) {
        // Show confirmation dialog
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete Contact")
            .setMessage("Are you sure you want to delete this contact?")
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Delete") { _, _ ->
                contactAdapter.removeContact(position)
                saveContacts()
                Snackbar.make(binding.root, "Contact deleted", Snackbar.LENGTH_SHORT).show()
            }
            .show()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
    }
}