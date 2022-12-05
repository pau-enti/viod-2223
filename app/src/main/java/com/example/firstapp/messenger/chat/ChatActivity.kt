package com.example.firstapp.messenger.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatRecyclerViewAdapter

    companion object {
        const val EXTRA_USER_ID = "EXTRA_USER_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ChatRecyclerViewAdapter(binding.chatView.layoutManager)
        binding.chatView.adapter = adapter

        val user = intent.extras?.getString(EXTRA_USER_ID) ?: return
        supportActionBar?.title = user


    }
}