package com.example.firstapp.sharedprefs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.firstapp.databinding.ActivitySharedPreferencesBinding

class SharedPreferencesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySharedPreferencesBinding
    private val viewModel: SharedPreferencesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.musicSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.writeMusicState(this, isChecked)
        }

        viewModel.musicState.observe(this) {
            binding.musicSwitch.isChecked = it
        }

        viewModel.readMusicState(this)
    }
}