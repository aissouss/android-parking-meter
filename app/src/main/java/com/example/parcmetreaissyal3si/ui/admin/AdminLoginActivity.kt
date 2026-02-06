package com.example.parcmetreaissyal3si.ui.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parcmetreaissyal3si.R
import com.example.parcmetreaissyal3si.databinding.ActivityAdminLoginBinding

class AdminLoginActivity : AppCompatActivity() {

    companion object {
        private const val PIN_CODE = "1234"
    }

    private lateinit var binding: ActivityAdminLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConnexion.setOnClickListener {
            val pin = binding.etPin.text.toString()
            if (pin == PIN_CODE) {
                startActivity(Intent(this, AdminDashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, R.string.admin_pin_erreur, Toast.LENGTH_SHORT).show()
                binding.etPin.text?.clear()
            }
        }
    }
}
