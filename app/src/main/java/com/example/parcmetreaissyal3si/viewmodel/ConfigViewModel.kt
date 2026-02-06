package com.example.parcmetreaissyal3si.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.parcmetreaissyal3si.data.AppDatabase
import com.example.parcmetreaissyal3si.data.Config
import com.example.parcmetreaissyal3si.repository.ParcmetreRepository
import kotlinx.coroutines.launch

class ConfigViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ParcmetreRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = ParcmetreRepository(db.configDao(), db.sessionDao())
    }

    val config: LiveData<Config?> = repository.config

    private val _saveSuccess = MutableLiveData<Boolean>()
    val saveSuccess: LiveData<Boolean> = _saveSuccess

    fun saveConfig(config: Config) {
        viewModelScope.launch {
            repository.updateConfig(config)
            _saveSuccess.postValue(true)
        }
    }

    fun clearSaveSuccess() {
        _saveSuccess.value = false
    }
}
