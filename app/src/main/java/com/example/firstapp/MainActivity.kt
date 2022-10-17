package com.example.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.helloButton)
        val text: TextView = findViewById(R.id.helloText)

        button.setOnClickListener {
            Toast.makeText(this, "Hola classe", Toast.LENGTH_LONG).show()
        }

        text.setOnLongClickListener {
            val snack = Snackbar.make(this, it, "Vols canviar el text?", Snackbar.LENGTH_LONG)
            snack.setAction("Sí") {
                text.text = "Text canviat"
            }
            snack.show()

            true
        }
    }
}
