package com.example.parcmetreaissyal3si.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session")
data class Session(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dateHeure: Long,
    val montant: Float,
    val acheve: Boolean
)
