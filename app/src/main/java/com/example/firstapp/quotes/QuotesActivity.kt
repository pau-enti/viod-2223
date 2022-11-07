package com.example.firstapp.quotes

import android.animation.ObjectAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firstapp.databinding.ActivityQuotesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextQuouteButton.setOnClickListener {
            getNextQuote()
        }

        binding.quoteTextView.text = ""
        binding.authorTextView.text = ""
        getNextQuote()
    }

    fun getNextQuote() {
        val outside = Retrofit.Builder()
            .baseUrl("http://quotes.stormconsultancy.co.uk/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val call = outside.create(ApiQuotes::class.java)
        transitionToRandomColor()

        call.getRandomQuote().enqueue(object : Callback<Quote> {
            override fun onResponse(call: Call<Quote>, response: Response<Quote>) {
                val quote = response.body() ?: return Toast.makeText(
                    this@QuotesActivity,
                    "Something went wrong :(",
                    Toast.LENGTH_SHORT
                ).show()
                binding.quoteTextView.text = quote.text
                binding.authorTextView.text = quote.author
            }

            override fun onFailure(call: Call<Quote>, t: Throwable) {
                Toast.makeText(this@QuotesActivity, "Something went wrong :(", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private var lastColor: Int = 0
    private fun transitionToRandomColor() {
        val newColor: Int =
            Color.argb(155, (0..256).random(), (0..256).random(), (0..256).random())

        val animator = ObjectAnimator.ofArgb(
            binding.quotesLayout,
            "backgroundColor",
            lastColor,
            newColor
        )

        animator.duration = 2000
        animator.start()

        lastColor = newColor
    }
}