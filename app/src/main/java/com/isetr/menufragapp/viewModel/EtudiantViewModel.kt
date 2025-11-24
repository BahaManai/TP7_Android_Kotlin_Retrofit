package com.isetr.menufragapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isetr.menufragapp.data.Etudiant
import com.isetr.menufragapp.data.EtudiantRepository
import kotlinx.coroutines.launch

class EtudiantViewModel(private val repository: EtudiantRepository) : ViewModel() {

    private val _etudiants = MutableLiveData<List<Etudiant>?>()
    val etudiants: LiveData<List<Etudiant>?> = _etudiants

    private val _etudiantSelectionne = MutableLiveData<Etudiant?>()
    val etudiantSelectionne: LiveData<Etudiant?> = _etudiantSelectionne

    private val _updateResult = MutableLiveData<Boolean?>()
    val updateResult: LiveData<Boolean?> = _updateResult

    private val _deleteResult = MutableLiveData<Boolean?>()
    val deleteResult: LiveData<Boolean?> = _deleteResult

    private val _addResult = MutableLiveData<Etudiant?>()
    val addResult: LiveData<Etudiant?> = _addResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        loadEtudiants()
    }

    fun loadEtudiants() {
        viewModelScope.launch {
            try {
                val response = repository.getEtudiants()
                _etudiants.postValue(if (response.isSuccessful) response.body() else null)
            } catch (e: Exception) {
                _errorMessage.postValue("Erreur réseau: ${e.message}")
            }
        }
    }

    fun addNewEtudiant(etudiant: Etudiant) {
        viewModelScope.launch {
            try {
                val response = repository.addEtudiant(etudiant)
                if (response.isSuccessful) {
                    _addResult.postValue(response.body())
                    loadEtudiants()
                } else {
                    _errorMessage.postValue("Erreur d\'ajout: ${response.code()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Erreur réseau: ${e.message}")
            }
        }
    }

    fun updateEtudiant(etudiant: Etudiant) {
        viewModelScope.launch {
            try {
                val response = repository.updateEtudiant(etudiant)
                _updateResult.postValue(response.isSuccessful)
                if (response.isSuccessful) {
                    loadEtudiants()
                }
            } catch (e: Exception) {
                _updateResult.postValue(false)
                _errorMessage.postValue("Erreur réseau: ${e.message}")
            }
        }
    }

    fun deleteEtudiant() {
        _etudiantSelectionne.value?.let { etudiant ->
            viewModelScope.launch {
                try {
                    val response = repository.deleteEtudiant(etudiant.cin)
                    _deleteResult.postValue(response.isSuccessful)
                    if (response.isSuccessful) {
                        loadEtudiants()
                    }
                } catch (e: Exception) {
                    _deleteResult.postValue(false)
                    _errorMessage.postValue("Erreur réseau: ${e.message}")
                }
            }
        }
    }

    fun selectionnerEtudiant(etudiant: Etudiant) {
        _etudiantSelectionne.postValue(etudiant)
    }

    // Les fonctions de consommation mettent maintenant la valeur à null
    fun onUpdateResultConsumed() { _updateResult.value = null }
    fun onDeleteResultConsumed() { _deleteResult.value = null }
}