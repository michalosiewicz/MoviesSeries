package com.micosi.moviesseries.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.micosi.moviesseries.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}