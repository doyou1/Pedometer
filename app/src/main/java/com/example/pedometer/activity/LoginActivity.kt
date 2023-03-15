package com.example.pedometer.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pedometer.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickEvent()

        init()
    }

    private fun init() {
        binding.showExistId = false
    }


    private fun setClickEvent() {
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.swiChangeIdType.setOnCheckedChangeListener { _, isChecked ->
            binding.showExistId = isChecked
        }
    }
}