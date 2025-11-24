package com.isetr.menufragapp.data

import com.isetr.menufragapp.reseau.EtudiantApi

/**
 * Le Repository, mis à jour pour inclure les opérations de mise à jour et de suppression.
 */
class EtudiantRepository(private val api: EtudiantApi) {

    suspend fun getEtudiants() = api.getEtudiants()

    suspend fun addEtudiant(etudiant: Etudiant) = api.addEtudiant(etudiant)

    suspend fun updateEtudiant(etudiant: Etudiant) = api.updateEtudiant(etudiant)

    suspend fun deleteEtudiant(cin: Int) = api.deleteEtudiant(cin)
}
