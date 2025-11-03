package com.isetr.menufragapp.data

/**
 * Modèle de données pour un étudiant.
 * Utilise 'data class' pour obtenir automatiquement des méthodes utiles comme equals() et copy().
 */
data class Etudiant(
    val identifiant: String,
    val classe: String,
    val email: String
)