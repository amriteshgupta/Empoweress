package com.example.empoweress

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.empoweress.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    lateinit var binding: ActivitySettingBinding

    private lateinit var sharedPref: SharedPreferences
    private lateinit var adapter: ArrayAdapter<String>
    private val contactList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = this.getSharedPreferences("trusted_contacts",Context.MODE_PRIVATE)!!

        binding.btnAddContact.setOnClickListener {
            addContact(binding.etContact.text.toString())
            loadContacts()
        }


        adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,contactList)
        binding.lvContacts.adapter=adapter

        loadContacts()

    }

    private fun addContact(contact: String) {
    if (!contactList.contains(contact)) {
        contactList.add(contact)
        sharedPref.edit().putStringSet("contactList", contactList.toSet()).apply()
        adapter.notifyDataSetChanged()
    } else {
        Toast.makeText(this, "Contact already exists", Toast.LENGTH_SHORT).show()
    }
}

    fun loadContacts(){
    contactList.clear()
        val contact=sharedPref.getStringSet("contactList", setOf())
        if (contact!=null){
        contactList.addAll(contact)
        adapter.notifyDataSetChanged()}
    }
}


