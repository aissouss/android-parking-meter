package com.example.parcmetreaissyal3si.ui.config

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.parcmetreaissyal3si.R
import com.example.parcmetreaissyal3si.data.Config
import com.example.parcmetreaissyal3si.databinding.ActivityConfigBinding
import com.example.parcmetreaissyal3si.viewmodel.ConfigViewModel

class ConfigActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfigBinding
    private lateinit var viewModel: ConfigViewModel
    private var currentConfigId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ConfigViewModel::class.java]

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.config.observe(this) { config ->
            config?.let { fillForm(it) }
        }

        viewModel.saveSuccess.observe(this) { success ->
            if (success) {
                Toast.makeText(this, R.string.config_sauvegarde_ok, Toast.LENGTH_SHORT).show()
                viewModel.clearSaveSuccess()
            }
        }
    }

    private fun fillForm(config: Config) {
        currentConfigId = config.id
        binding.etPrixMinute.setText(config.prixMinute.toString())
        binding.etTimeout.setText(config.timeOut.toString())
        binding.etMontantMin.setText(config.montantMin.toString())
        binding.switchEtat.isChecked = config.etat == "En service"
        updateEtatLabel(config.etat == "En service")
        binding.etAdresseIP.setText(config.adresseIP)
        binding.etLatitude.setText(config.latitude.toString())
        binding.etLongitude.setText(config.longitude.toString())
        binding.etTickets.setText(config.ticketsRestants.toString())
        binding.etTitre.setText(config.titre)
        binding.etZone.setText(config.zone)
    }

    private fun setupClickListeners() {
        binding.switchEtat.setOnCheckedChangeListener { _, isChecked ->
            updateEtatLabel(isChecked)
        }

        binding.btnSauvegarder.setOnClickListener {
            saveConfig()
        }
    }

    private fun updateEtatLabel(enService: Boolean) {
        if (enService) {
            binding.tvEtatLabel.text = getString(R.string.config_en_service)
            binding.tvEtatLabel.setTextColor(getColor(R.color.status_en_service))
        } else {
            binding.tvEtatLabel.text = getString(R.string.config_hors_service)
            binding.tvEtatLabel.setTextColor(getColor(R.color.status_hors_service))
        }
    }

    private fun saveConfig() {
        val prixMinute = binding.etPrixMinute.text.toString().toIntOrNull() ?: 10
        val timeout = binding.etTimeout.text.toString().toIntOrNull() ?: 60
        val montantMin = binding.etMontantMin.text.toString().toIntOrNull() ?: 1000
        val etat = if (binding.switchEtat.isChecked) "En service" else "Hors service"
        val adresseIP = binding.etAdresseIP.text.toString().ifBlank { "10.10.2.25" }
        val latitude = binding.etLatitude.text.toString().toDoubleOrNull() ?: 36.8065
        val longitude = binding.etLongitude.text.toString().toDoubleOrNull() ?: 10.1815
        val tickets = binding.etTickets.text.toString().toIntOrNull() ?: 100
        val titre = binding.etTitre.text.toString().ifBlank { "Municipalité de Tunis" }
        val zone = binding.etZone.text.toString().ifBlank { "Lafayette" }

        val config = Config(
            id = currentConfigId,
            prixMinute = prixMinute,
            timeOut = timeout,
            montantMin = montantMin,
            etat = etat,
            adresseIP = adresseIP,
            latitude = latitude,
            longitude = longitude,
            ticketsRestants = tickets,
            titre = titre,
            zone = zone
        )

        viewModel.saveConfig(config)
    }
}
