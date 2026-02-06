package com.example.parcmetreaissyal3si.ui.client

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.parcmetreaissyal3si.databinding.ActivityClientBinding
import com.example.parcmetreaissyal3si.viewmodel.ClientViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

class ClientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientBinding
    private lateinit var viewModel: ClientViewModel
    private var countDownTimer: CountDownTimer? = null
    private var dateTimeTimer: Timer? = null
    private var toneGenerator: ToneGenerator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ClientViewModel::class.java]

        try {
            toneGenerator = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)
        } catch (_: Exception) { }

        setupDateTimeUpdater()
        setupObservers()
        setupClickListeners()
    }

    private fun setupDateTimeUpdater() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE)
        dateTimeTimer = Timer()
        dateTimeTimer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    binding.tvDateTime.text = dateFormat.format(Date())
                }
            }
        }, 0, 1000)
    }

    private fun setupObservers() {
        viewModel.solde.observe(this) { solde ->
            val montantDT = String.format(Locale.FRANCE, "%.3f", solde / 1000.0)
            binding.tvMontant.text = "$montantDT DT"
        }

        viewModel.config.observe(this) { config ->
            if (config?.etat == "Hors service") {
                afficherHorsService()
            } else {
                afficherEnService()
            }
        }

        viewModel.ticketMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                cancelTimer()
                viewModel.clearTicketMessage()
            }
        }

        viewModel.errorMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.clearErrorMessage()
            }
        }

        viewModel.cancelMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                cancelTimer()
                viewModel.clearCancelMessage()
            }
        }

        viewModel.timeoutMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                viewModel.clearTimeoutMessage()
            }
        }

        viewModel.resetTimer.observe(this) { shouldReset ->
            if (shouldReset == true) {
                startTimeoutTimer()
                viewModel.clearResetTimer()
            }
        }

        viewModel.sessionStarted.observe(this) { started ->
            binding.progressTimer.visibility = if (started) View.VISIBLE else View.GONE
            if (!started) {
                binding.progressTimer.progress = 0
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnCoin100.setOnClickListener {
            viewModel.insertCoin(100)
            playInsertSound()
        }
        binding.btnCoin200.setOnClickListener {
            viewModel.insertCoin(200)
            playInsertSound()
        }
        binding.btnCoin500.setOnClickListener {
            viewModel.insertCoin(500)
            playInsertSound()
        }
        binding.btnCoin1000.setOnClickListener {
            viewModel.insertCoin(1000)
            playInsertSound()
        }
        binding.btnValider.setOnClickListener {
            viewModel.valider()
        }
        binding.btnAnnuler.setOnClickListener {
            viewModel.annuler()
        }
    }

    private fun afficherHorsService() {
        binding.layoutHorsService.visibility = View.VISIBLE
        binding.layoutClient.visibility = View.GONE
        cancelTimer()
    }

    private fun afficherEnService() {
        binding.layoutHorsService.visibility = View.GONE
        binding.layoutClient.visibility = View.VISIBLE
    }

    private fun startTimeoutTimer() {
        cancelTimer()
        val timeoutSeconds = viewModel.config.value?.timeOut?.toLong() ?: 60L
        val totalMillis = timeoutSeconds * 1000

        binding.progressTimer.max = 100
        binding.progressTimer.progress = 100

        countDownTimer = object : CountDownTimer(totalMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = ((millisUntilFinished.toFloat() / totalMillis) * 100).toInt()
                binding.progressTimer.progress = progress

                val secondsLeft = millisUntilFinished / 1000
                if (secondsLeft <= 10) {
                    try {
                        toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 200)
                    } catch (_: Exception) { }
                }
            }

            override fun onFinish() {
                binding.progressTimer.progress = 0
                viewModel.onTimeout()
            }
        }.start()
    }

    private fun cancelTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
    }

    private fun playInsertSound() {
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 150)
        } catch (_: Exception) { }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelTimer()
        dateTimeTimer?.cancel()
        toneGenerator?.release()
    }
}
