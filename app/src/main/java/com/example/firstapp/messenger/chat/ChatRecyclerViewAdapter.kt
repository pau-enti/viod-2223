package com.example.firstapp.messenger.chat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R
import com.example.firstapp.databinding.ItemReceivedMessageBinding
import com.example.firstapp.databinding.ItemSentMessageBinding
import com.example.firstapp.messenger.chat.model.Chat
import com.example.firstapp.messenger.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth


@SuppressLint("NotifyDataSetChanged")
class ChatRecyclerViewAdapter(
    private val layoutManager: RecyclerView.LayoutManager?
) :
    RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatVH>() {

    var chat: Chat? = null

    fun notifyNewMessages(chat: Chat) {
        this.chat = chat
        notifyNewMessage()
    }

    fun notifyNewMessage() {
        val messages = chat?.messages ?: return
        notifyDataSetChanged()
        layoutManager?.scrollToPosition(messages.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatVH {
        val layoutInflater = LayoutInflater.from(parent.context)

        val view = if (viewType == SENT_TYPE)
            layoutInflater.inflate(R.layout.item_sent_message, parent, false)
        else
            layoutInflater.inflate(R.layout.item_received_message, parent, false)

        return ChatVH(view, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        // 0 -> sent message
        // 1 -> received message
        val messages = chat?.messages ?: return 0
        val author = LoginActivity.currentUser
        return if (messages[position].author == author) SENT_TYPE else RECEIVED_TYPE
    }

    override fun onBindViewHolder(holder: ChatVH, position: Int) {
        val messages = chat?.messages ?: return
        holder.message.text = messages[position].content
    }

    override fun getItemCount(): Int = chat?.messages?.size ?: 0

    inner class ChatVH(view: View, type: Int) : RecyclerView.ViewHolder(view) {
        val message: TextView = if (type == SENT_TYPE)
            ItemSentMessageBinding.bind(view).myMessage
        else
            ItemReceivedMessageBinding.bind(view).othersMessage
    }

    companion object {
        const val SENT_TYPE = 0
        const val RECEIVED_TYPE = 1
    }
}