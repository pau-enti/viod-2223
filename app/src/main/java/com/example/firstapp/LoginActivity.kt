package com.example.firstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firstapp.databinding.ActivityLoginBinding
import com.example.firstapp.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    
    val CORRECT_USERNAME = "pau@enti.cat"
    val CORRECT_PASSWORD = "123456"

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.loginButton.setOnClickListener { 
            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()

            Toast.makeText(this, "user: $username, pass: $password", Toast.LENGTH_SHORT).show()
            
            if (username == CORRECT_USERNAME && password == CORRECT_PASSWORD) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Incorrect user or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}