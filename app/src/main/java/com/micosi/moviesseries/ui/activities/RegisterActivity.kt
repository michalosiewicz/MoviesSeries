package com.micosi.moviesseries.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.micosi.moviesseries.R
import com.micosi.moviesseries.databinding.ActivityMainBinding
import com.micosi.moviesseries.databinding.ActivityRegisterBinding
import com.micosi.moviesseries.db.DBReference
import com.micosi.moviesseries.db.DBReference.authFB

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        if (isCurrentUser()) {
            startMainActivity()
        }
    }

    private fun isCurrentUser() = authFB.currentUser != null

    private fun startMainActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }
}