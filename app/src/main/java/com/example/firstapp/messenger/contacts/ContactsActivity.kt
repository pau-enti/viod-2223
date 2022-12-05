package com.example.firstapp.messenger.contacts

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityContactsBinding
import com.example.firstapp.messenger.contacts.model.Contact
import com.example.firstapp.messenger.login.LoginActivity
import com.google.android.gms.ads.*
import com.google.firebase.auth.FirebaseAuth


class ContactsActivity : AppCompatActivity() {

    lateinit var binding: ActivityContactsBinding
    private lateinit var adapter: ContactsRecyclerViewAdapter

    private val contacts = ArrayList<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ContactsRecyclerViewAdapter(this)
        binding.contactsRecyclerView.adapter = adapter

        binding.addContactButton.setOnClickListener {
            val newContact = binding.newContact.text.toString()
            binding.newContact.text?.clear()
            contacts.add(Contact(newContact, newContact))
            adapter.updateContacts(contacts)
        }

        MobileAds.initialize(this)
    }

    override fun onResume() {
        super.onResume()

        val request = AdRequest.Builder().build()
        binding.adView.loadAd(request)

        adapter.loadAd()
//        binding.adView.adListener = object : AdListener() {
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_contacts, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_logout -> logout()
        }
        return true
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut() // Instant operation

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        finish()
    }
}