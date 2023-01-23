package com.example.firstapp.realtimedatabase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatViewModel : ViewModel() {

    private var database: DatabaseReference =
        Firebase.database("https://viod-2223-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("chats")

    var currentChat = MutableLiveData<Chat>()

    fun writeChat(chat: Chat) {
        database.child(chat.id).setValue(chat)

//        currentChat = chat
        currentChat.postValue(chat)
    }

    fun subscribeAt(chat: Chat) {
        database.child(chat.id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val c = snapshot.getValue(Chat::class.java) ?: return
                currentChat.postValue(c)
            }

            override fun onCancelled(error: DatabaseError) = Unit
        })
    }

}