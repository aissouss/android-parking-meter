package com.example.parcmetreaissyal3si.ui.admin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parcmetreaissyal3si.R
import com.example.parcmetreaissyal3si.databinding.ActivityAdminDashboardBinding
import com.example.parcmetreaissyal3si.ui.adapter.SessionAdapter
import com.example.parcmetreaissyal3si.viewmodel.AdminViewModel

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminDashboardBinding
    private lateinit var viewModel: AdminViewModel
    private lateinit var sessionAdapter: SessionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AdminViewModel::class.java]

        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        sessionAdapter = SessionAdapter()
        binding.rvSessions.apply {
            layoutManager = LinearLayoutManager(this@AdminDashboardActivity)
            adapter = sessionAdapter
        }
    }

    private fun setupObservers() {
        viewModel.config.observe(this) { config ->
            config?.let {
                binding.tvEtat.text = it.etat
                if (it.etat == "En service") {
                    binding.tvEtat.setTextColor(getColor(R.color.status_en_service))
                } else {
                    binding.tvEtat.setTextColor(getColor(R.color.status_hors_service))
                }

                binding.tvTicketsRestants.text = it.ticketsRestants.toString()

                binding.tvPannePapier.visibility =
                    if (it.ticketsRestants <= 0) View.VISIBLE else View.GONE

                binding.tvPanneMonnaie.visibility =
                    if (it.panneMonnaie) View.VISIBLE else View.GONE
            }
        }

        viewModel.totalMontant.observe(this) { total ->
            val montant = total ?: 0f
            binding.tvTotalEncaisse.text = String.format("%.3f DT", montant)
        }

        viewModel.sessionCount.observe(this) { count ->
            binding.tvNbSessions.text = count.toString()
        }

        viewModel.allSessions.observe(this) { sessions ->
            sessionAdapter.submitList(sessions)
        }

        viewModel.actionMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.clearActionMessage()
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnViderCaisse.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(R.string.admin_confirm_vider_caisse_titre)
                .setMessage(R.string.admin_confirm_vider_caisse_message)
                .setPositiveButton(R.string.admin_confirm_oui) { _, _ ->
                    viewModel.viderCaisse()
                }
                .setNegativeButton(R.string.admin_confirm_non, null)
                .show()
        }

        binding.btnChangerBobine.setOnClickListener {
            viewModel.changerBobine()
        }

        binding.btnToggleEtat.setOnClickListener {
            viewModel.toggleEtat()
        }

        binding.btnTogglePanneMonnaie.setOnClickListener {
            viewModel.togglePanneMonnaie()
        }
    }
}
