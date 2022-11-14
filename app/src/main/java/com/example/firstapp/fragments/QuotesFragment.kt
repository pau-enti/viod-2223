package com.example.firstapp.fragments

import android.animation.ObjectAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.firstapp.databinding.ActivityQuotesBinding
import com.example.firstapp.quotes.ApiQuotes
import com.example.firstapp.quotes.Quote
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuotesFragment : Fragment() {

    private lateinit var binding: ActivityQuotesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityQuotesBinding.inflate(layoutInflater)

        binding.nextQuouteButton.setOnClickListener {
            getNextQuote()
        }

        binding.quoteTextView.text = ""
        binding.authorTextView.text = ""
        getNextQuote()

        return binding.root
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
                    context,
                    "Something went wrong :(",
                    Toast.LENGTH_SHORT
                ).show()
                binding.quoteTextView.text = quote.text
                binding.authorTextView.text = quote.author
            }

            override fun onFailure(call: Call<Quote>, t: Throwable) {
                Toast.makeText(context, "Something went wrong :(", Toast.LENGTH_SHORT)
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