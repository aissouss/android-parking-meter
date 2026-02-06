package com.example.parcmetreaissyal3si.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "config")
data class Config(
    @PrimaryKey val id: Int = 1,
    val prixMinute: Int = 10,
    val timeOut: Int = 60,
    val montantMin: Int = 1000,
    val etat: String = "En service",
    val adresseIP: String = "10.10.2.25",
    val latitude: Double = 36.8065,
    val longitude: Double = 10.1815,
    val ticketsRestants: Int = 100,
    val titre: String = "Municipalité de Tunis",
    val zone: String = "Lafayette",
    val panneMonnaie: Boolean = false
)
