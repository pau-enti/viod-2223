package com.example.firstapp.messenger.contacts

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firstapp.messenger.contacts.model.Contact
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class ContactsViewModel : ViewModel() {

    val contacts = MutableLiveData<ArrayList<Contact>>()
    private val FILENAME = "contacts.dat"

    fun saveData(context: Context) {
        context.openFileOutput(FILENAME, Context.MODE_PRIVATE).use {
            val oos = ObjectOutputStream(it)
            oos.writeObject(contacts.value ?: return)
        }
    }

    fun readData(context: Context) {
        try {
            context.openFileInput(FILENAME).use {
                val iin = ObjectInputStream(it)
                val data = iin.readObject()
                if (data is ArrayList<*>) {
                    val cs = data.filterIsInstance<Contact>() as ArrayList
                    contacts.value = cs
                }
            }
        } catch (e: IOException) {
            // pass
        }

        if (contacts.value == null) {
            contacts.postValue(arrayListOf())
        }
    }

    fun addContact(contact: Contact) {
        contacts.value?.add(contact)
        contacts.postValue(contacts.value)
    }

    fun removeContact(contact: Contact) {
        contacts.value?.remove(contact)
        contacts.postValue(contacts.value)
    }


}