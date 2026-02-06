package com.example.parcmetreaissyal3si

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.parcmetreaissyal3si.databinding.ActivityMainBinding
import com.example.parcmetreaissyal3si.ui.admin.AdminLoginActivity
import com.example.parcmetreaissyal3si.ui.client.ClientActivity
import com.example.parcmetreaissyal3si.ui.config.ConfigActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClient.setOnClickListener {
            startActivity(Intent(this, ClientActivity::class.java))
        }

        binding.btnConfig.setOnClickListener {
            startActivity(Intent(this, ConfigActivity::class.java))
        }

        binding.btnAdmin.setOnClickListener {
            startActivity(Intent(this, AdminLoginActivity::class.java))
        }
    }
}
