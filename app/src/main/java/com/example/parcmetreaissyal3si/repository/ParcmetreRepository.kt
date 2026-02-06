package com.example.parcmetreaissyal3si.repository

import androidx.lifecycle.LiveData
import com.example.parcmetreaissyal3si.data.Config
import com.example.parcmetreaissyal3si.data.ConfigDao
import com.example.parcmetreaissyal3si.data.Session
import com.example.parcmetreaissyal3si.data.SessionDao

class ParcmetreRepository(
    private val configDao: ConfigDao,
    private val sessionDao: SessionDao
) {
    // Config
    val config: LiveData<Config?> = configDao.getConfig()

    suspend fun getConfigDirect(): Config? = configDao.getConfigDirect()

    suspend fun insertConfig(config: Config) = configDao.insertConfig(config)

    suspend fun updateConfig(config: Config) = configDao.updateConfig(config)

    suspend fun decrementTickets() = configDao.decrementTickets()

    suspend fun setTicketsRestants(count: Int) = configDao.setTicketsRestants(count)

    suspend fun setEtat(etat: String) = configDao.setEtat(etat)

    suspend fun setPanneMonnaie(panne: Boolean) = configDao.setPanneMonnaie(panne)

    // Sessions
    val allSessions: LiveData<List<Session>> = sessionDao.getAllSessions()

    val totalMontant: LiveData<Float?> = sessionDao.getTotalMontant()

    val sessionCount: LiveData<Int> = sessionDao.getSessionCount()

    suspend fun insertSession(session: Session): Long = sessionDao.insertSession(session)

    suspend fun deleteAllSessions() = sessionDao.deleteAllSessions()
}
