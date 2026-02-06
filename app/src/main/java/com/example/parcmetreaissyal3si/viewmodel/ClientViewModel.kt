package com.example.parcmetreaissyal3si.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.parcmetreaissyal3si.data.AppDatabase
import com.example.parcmetreaissyal3si.data.Session
import com.example.parcmetreaissyal3si.repository.ParcmetreRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ClientViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ParcmetreRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = ParcmetreRepository(db.configDao(), db.sessionDao())
    }

    val config = repository.config

    private val _solde = MutableLiveData(0)
    val solde: LiveData<Int> = _solde

    private val _sessionStarted = MutableLiveData(false)
    val sessionStarted: LiveData<Boolean> = _sessionStarted

    private val _ticketMessage = MutableLiveData<String?>()
    val ticketMessage: LiveData<String?> = _ticketMessage

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _cancelMessage = MutableLiveData<String?>()
    val cancelMessage: LiveData<String?> = _cancelMessage

    private val _timeoutMessage = MutableLiveData<String?>()
    val timeoutMessage: LiveData<String?> = _timeoutMessage

    private val _resetTimer = MutableLiveData<Boolean>()
    val resetTimer: LiveData<Boolean> = _resetTimer

    private var sessionDateHeure: Long = 0L

    fun insertCoin(valueMillimes: Int) {
        if (_sessionStarted.value != true) {
            _sessionStarted.value = true
            sessionDateHeure = System.currentTimeMillis()
        }
        _solde.value = (_solde.value ?: 0) + valueMillimes
        _resetTimer.value = true
    }

    fun valider() {
        val currentSolde = _solde.value ?: 0
        val cfg = config.value ?: return

        if (currentSolde < cfg.montantMin) {
            val minDT = String.format(Locale.FRANCE, "%.3f", cfg.montantMin / 1000.0)
            _errorMessage.value = "Montant minimum requis: $minDT DT"
            _resetTimer.value = true
            return
        }

        viewModelScope.launch {
            val montantDT = currentSolde / 1000.0f
            val session = Session(
                dateHeure = sessionDateHeure,
                montant = montantDT,
                acheve = true
            )
            val sessionId = repository.insertSession(session)
            repository.decrementTickets()

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
            val timeFormat = SimpleDateFormat("HH:mm", Locale.FRANCE)
            val date = Date(sessionDateHeure)
            val dateStr = dateFormat.format(date)
            val heureStr = timeFormat.format(date)

            val prixMinute = cfg.prixMinute
            val minutesAchetees = if (prixMinute > 0) (currentSolde / prixMinute) else 0
            val now = System.currentTimeMillis()
            val heureFin = Date(now + minutesAchetees * 60 * 1000L)
            val heureFinStr = timeFormat.format(heureFin)
            val dateFinStr = dateFormat.format(heureFin)

            val ticket = """
═══════════════════════
  ${cfg.titre}
  $dateStr à $heureStr
  ID: $sessionId
───────────────────────
  $dateFinStr
  $heureFinStr
───────────────────────
  Zone: ${cfg.zone}
═══════════════════════
            """.trimIndent()

            _ticketMessage.postValue(ticket)
            resetState()
        }
    }

    fun annuler() {
        val currentSolde = _solde.value ?: 0
        if (currentSolde > 0) {
            val montantDT = String.format(Locale.FRANCE, "%.3f", currentSolde / 1000.0)
            _cancelMessage.value = "Récupérez votre monnaie: $montantDT DT"
        }
        resetState()
    }

    fun onTimeout() {
        val currentSolde = _solde.value ?: 0
        if (currentSolde > 0 && _sessionStarted.value == true) {
            viewModelScope.launch {
                val montantDT = currentSolde / 1000.0f
                val session = Session(
                    dateHeure = sessionDateHeure,
                    montant = montantDT,
                    acheve = false
                )
                repository.insertSession(session)
                _timeoutMessage.postValue("Temps écoulé - Transaction enregistrée comme inachevée")
                resetState()
            }
        }
    }

    private fun resetState() {
        _solde.postValue(0)
        _sessionStarted.postValue(false)
        sessionDateHeure = 0L
    }

    fun clearTicketMessage() {
        _ticketMessage.value = null
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    fun clearCancelMessage() {
        _cancelMessage.value = null
    }

    fun clearTimeoutMessage() {
        _timeoutMessage.value = null
    }

    fun clearResetTimer() {
        _resetTimer.value = false
    }
}
