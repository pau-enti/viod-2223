package com.example.firstapp.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityBottomBarBinding

class BottomBarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firstFragment = FirstFragment()
        val imageFragment = ImageFragment()
        val quotesFragment = QuotesFragment()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.firstButton -> setFragment(firstFragment)
                R.id.secondButton -> setFragment(imageFragment)
                R.id.thirdButton -> setFragment(quotesFragment)
            }
            true
        }

        setFragment(firstFragment)
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            supportFragmentManager.fragments.forEach {
                hide(it)
            }

            if (!fragment.isAdded)
                add(binding.fragmentContainer.id, fragment)
            else
                show(fragment)

            commit()
        }
    }
}