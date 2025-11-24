package com.isetr.menufragapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.isetr.menufragapp.data.EtudiantRepository

/**
 * Factory pour créer une instance de EtudiantViewModel.
 * C'est nécessaire car notre ViewModel a maintenant un constructeur avec un paramètre (le Repository),
 * et le système a besoin d'aide pour l'instancier.
 */
class EtudiantViewModelFactory(private val repository: EtudiantRepository) : ViewModelProvider.Factory {

    // Cette méthode est appelée par le système pour créer le ViewModel.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // On vérifie si la classe demandée est bien notre EtudiantViewModel.
        if (modelClass.isAssignableFrom(EtudiantViewModel::class.java)) {
            // Si oui, on retourne une nouvelle instance en lui passant le repository.
            @Suppress("UNCHECKED_CAST")
            return EtudiantViewModel(repository) as T
        }
        // Sinon, on lève une exception pour signaler une erreur de configuration.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
