package com.example.firstapp.messenger.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityLoginBinding
import com.example.firstapp.messenger.contacts.ContactsActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        val currentUser: String
            get() = FirebaseAuth.getInstance().currentUser?.email ?: "Anonymous"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null) {
            Toast.makeText(
                this,
                "Logged in as ${firebaseAuth.currentUser?.email}",
                Toast.LENGTH_SHORT
            ).show()
            login()
        }


        binding.loginButton.setOnClickListener {
            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()

            firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnSuccessListener {
                    login()
                }.addOnFailureListener {
                    Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT)
                        .show()
                }
        }

        binding.registerButton.setOnClickListener {
            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()

            firebaseAuth.createUserWithEmailAndPassword(username, password).addOnSuccessListener {
                Toast.makeText(this, "User $username created. Logging in", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(this, "An error occurred: $it", Toast.LENGTH_SHORT).show()
            }
        }

        binding.userInput.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                val username = binding.userInput.text.toString()
                if (!Patterns.EMAIL_ADDRESS.matcher(username).matches())
                    binding.userInput.error = "Invalid username"
                else
                    binding.userInput.error = null
            }
        }
    }

    private fun login() {
        val intent = Intent(this@LoginActivity, ContactsActivity::class.java)
        startActivity(intent)

        finish()
    }
}