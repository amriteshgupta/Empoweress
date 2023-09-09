package com.example.empoweress

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingActivity : AppCompatActivity() {

    private lateinit var etContact: EditText
    private lateinit var btnAddContact: Button
    private lateinit var lvContacts: ListView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: ArrayAdapter<String>
    private val contactList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        etContact = findViewById(R.id.etContact)
        btnAddContact = findViewById(R.id.btnAddContact)
        lvContacts = findViewById(R.id.lvContacts)

        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        // Load contacts from SharedPreferences
        loadContacts()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactList)
        lvContacts.adapter = adapter

        btnAddContact.setOnClickListener {
            val contact = etContact.text.toString()
            if (contact.isNotEmpty()) {
                addContact(contact)
                etContact.text.clear()
            } else {
                Toast.makeText(this, "Please enter a contact number", Toast.LENGTH_SHORT).show()
            }
        }

        lvContacts.setOnItemLongClickListener { _, _, position, _ ->
            removeContact(position)
            true
        }
    }

    private fun loadContacts() {
        val contacts = sharedPreferences.getStringSet("trusted_contacts", setOf())
        contactList.clear()
        contactList.addAll(contacts!!)
    }

    private fun saveContacts() {
        val editor = sharedPreferences.edit()
        editor.putStringSet("trusted_contacts", contactList.toSet())
        editor.apply()
    }

    private fun addContact(contact: String) {
        if (!contactList.contains(contact)) {
            contactList.add(contact)
            adapter.notifyDataSetChanged()
            saveContacts()
            Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Contact already exists", Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeContact(position: Int) {
        val contact = contactList[position]
        contactList.removeAt(position)
        adapter.notifyDataSetChanged()
        saveContacts()
        Toast.makeText(this, "Contact removed: $contact", Toast.LENGTH_SHORT).show()
    }
}
