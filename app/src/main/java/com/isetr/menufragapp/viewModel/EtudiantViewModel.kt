package com.isetr.menufragapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isetr.menufragapp.data.Etudiant
class EtudiantViewModel : ViewModel() {
    // Utilisation de MutableLiveData pour que la liste soit observable et modifiable
    private val _etudiants = MutableLiveData<MutableList<Etudiant>>()
    val etudiants: LiveData<MutableList<Etudiant>> = _etudiants

    val etudiantSelectionne = MutableLiveData<Etudiant?>()

    init { // Initialisation de la liste (ou chargement depuis une source)
        _etudiants.value = mutableListOf(
            Etudiant("101", "M1 Info", "ali@isetr.tn"),
            Etudiant("102", "M2 Info", "amel@isetr.tn"),
            // ... autres étudiants
        ) }
    // Fonction pour ajouter un étudiant.
    // Notifie automatiquement les Observateurs (comme le Fragment Liste).
    fun addEtudiant(etudiant: Etudiant) {
        val currentList = _etudiants.value ?: mutableListOf()
        currentList.add(etudiant)
        _etudiants.value = currentList // Met à jour le LiveData
    }

    fun selectionnerEtudiant(etudiant: Etudiant) {
        etudiantSelectionne.value = etudiant // Notifie le DetailEtudiantFragment
    }
}