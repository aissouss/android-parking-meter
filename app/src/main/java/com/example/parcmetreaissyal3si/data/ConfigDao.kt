package com.example.parcmetreaissyal3si.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ConfigDao {

    @Query("SELECT * FROM config WHERE id = 1")
    fun getConfig(): LiveData<Config?>

    @Query("SELECT * FROM config WHERE id = 1")
    suspend fun getConfigDirect(): Config?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConfig(config: Config)

    @Update
    suspend fun updateConfig(config: Config)

    @Query("UPDATE config SET ticketsRestants = ticketsRestants - 1 WHERE id = 1")
    suspend fun decrementTickets()

    @Query("UPDATE config SET ticketsRestants = :count WHERE id = 1")
    suspend fun setTicketsRestants(count: Int)

    @Query("UPDATE config SET etat = :etat WHERE id = 1")
    suspend fun setEtat(etat: String)

    @Query("UPDATE config SET panneMonnaie = :panne WHERE id = 1")
    suspend fun setPanneMonnaie(panne: Boolean)
}
