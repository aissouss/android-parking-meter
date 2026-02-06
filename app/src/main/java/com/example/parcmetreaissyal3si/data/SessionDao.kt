package com.example.parcmetreaissyal3si.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SessionDao {

    @Insert
    suspend fun insertSession(session: Session): Long

    @Query("SELECT * FROM session ORDER BY dateHeure DESC")
    fun getAllSessions(): LiveData<List<Session>>

    @Query("SELECT SUM(montant) FROM session")
    fun getTotalMontant(): LiveData<Float?>

    @Query("SELECT COUNT(*) FROM session")
    fun getSessionCount(): LiveData<Int>

    @Query("DELETE FROM session")
    suspend fun deleteAllSessions()
}
