package com.isetr.menufragapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "etudiants")
data class Etudiant(
    @PrimaryKey
    val cin: Int,
    val nom: String,
    val prenom: String,
    val mail: String,
    val password: String,
    val classe: String
)
