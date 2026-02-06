package com.example.parcmetreaissyal3si.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.parcmetreaissyal3si.data.AppDatabase
import com.example.parcmetreaissyal3si.data.Config
import com.example.parcmetreaissyal3si.data.Session
import com.example.parcmetreaissyal3si.repository.ParcmetreRepository
import kotlinx.coroutines.launch

class AdminViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ParcmetreRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = ParcmetreRepository(db.configDao(), db.sessionDao())
    }

    val config: LiveData<Config?> = repository.config
    val allSessions: LiveData<List<Session>> = repository.allSessions
    val totalMontant: LiveData<Float?> = repository.totalMontant
    val sessionCount: LiveData<Int> = repository.sessionCount

    private val _actionMessage = MutableLiveData<String?>()
    val actionMessage: LiveData<String?> = _actionMessage

    fun viderCaisse() {
        viewModelScope.launch {
            val total = totalMontant.value ?: 0f
            val totalStr = String.format("%.3f", total)
            repository.deleteAllSessions()
            _actionMessage.postValue("Caisse vidée. Total encaissé: $totalStr DT")
        }
    }

    fun changerBobine() {
        viewModelScope.launch {
            repository.setTicketsRestants(100)
            _actionMessage.postValue("Bobine changée. Tickets restants: 100")
        }
    }

    fun toggleEtat() {
        viewModelScope.launch {
            val currentConfig = repository.getConfigDirect()
            if (currentConfig != null) {
                val newEtat = if (currentConfig.etat == "En service") "Hors service" else "En service"
                repository.setEtat(newEtat)
                _actionMessage.postValue("État changé: $newEtat")
            }
        }
    }

    fun togglePanneMonnaie() {
        viewModelScope.launch {
            val currentConfig = repository.getConfigDirect()
            if (currentConfig != null) {
                val newPanne = !currentConfig.panneMonnaie
                repository.setPanneMonnaie(newPanne)
                val msg = if (newPanne) "Panne monnaie signalée" else "Panne monnaie résolue"
                _actionMessage.postValue(msg)
            }
        }
    }

    fun clearActionMessage() {
        _actionMessage.value = null
    }
}
