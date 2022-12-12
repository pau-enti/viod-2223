package com.example.firstapp.messenger.contacts

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R
import com.example.firstapp.databinding.ItemContactBinding
import com.example.firstapp.messenger.chat.ChatActivity
import com.example.firstapp.messenger.contacts.model.Contact
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class ContactsRecyclerViewAdapter(
    private val activity: Activity,
    private val contactsViewModel: ContactsViewModel,
) :
    RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactViewHolder>() {

    private var contacts: List<Contact> = listOf()
    private var ad: InterstitialAd? = null

    fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(activity, "ca-app-pub-3940256099942544/1033173712",
            adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    ad = null
                }

                override fun onAdLoaded(loadedAd: InterstitialAd) {
                    ad = loadedAd
                }
            })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(layoutInflater, parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.name.text = contact.name

        holder.itemView.setOnClickListener {
            val intent = Intent(activity, ChatActivity::class.java)
            intent.putExtra(ChatActivity.EXTRA_USER_ID, contact.userId)

            ad?.apply {
                fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        activity.startActivity(intent)
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        activity.startActivity(intent)
                    }

                    override fun onAdShowedFullScreenContent() {
                        ad = null
                    }
                }
                show(activity)
            } ?: activity.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            val dialog = AlertDialog.Builder(activity)
            dialog.setTitle(activity.getString(R.string.delete))
            dialog.setMessage(activity.getString(R.string.contacts_adapter_delete_confirmation, contact.name))

            dialog.setPositiveButton("Yes") { _, _ ->
                contactsViewModel.removeContact(contact)
            }
            dialog.setNegativeButton("No") { _, _ ->
                // pass
            }
            dialog.show()
            true
        }
    }

    override fun getItemCount(): Int = contacts.size

    inner class ContactViewHolder(binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val name: TextView = binding.contactName
        val image: ImageView = binding.contactImage
    }
}