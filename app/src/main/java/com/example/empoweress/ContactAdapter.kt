package com.example.empoweress

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class ContactAdapter(private val contacts: MutableList<String>) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    interface OnContactClickListener {
        fun onDeleteClick(position: Int)
    }

    var listener: OnContactClickListener? = null

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvContact: TextView = itemView.findViewById(R.id.tvContact)
        val btnDelete: MaterialButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.tvContact.text = contacts[position]

        holder.btnDelete.setOnClickListener {
            listener?.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int = contacts.size

    fun removeContact(position: Int) {
        if (position in contacts.indices) {
            contacts.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}