package com.example.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.firstapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.helloButton.setOnClickListener {
            Toast.makeText(this, "Hola classe", Toast.LENGTH_LONG).show()
        }

        binding.helloText.setOnLongClickListener {
            val snack = Snackbar.make(this, it, "Vols canviar el text?", Snackbar.LENGTH_LONG)
            snack.setAction("Sí") {
                binding.helloText.text = "Text canviat"
            }
            snack.show()

            true
        }
    }
}
