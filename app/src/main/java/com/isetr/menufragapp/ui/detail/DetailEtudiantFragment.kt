package com.isetr.menufragapp.ui.detail

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.isetr.menufragapp.data.Etudiant
import com.isetr.menufragapp.databinding.FragmentDetailEtudiantBinding
import com.isetr.menufragapp.viewModel.EtudiantViewModel

class DetailEtudiantFragment : Fragment() {

    private var _binding: FragmentDetailEtudiantBinding? = null
    private val binding get() = _binding!!

    private val etudiantViewModel: EtudiantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailEtudiantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observer l'étudiant sélectionné pour remplir les champs
        etudiantViewModel.etudiantSelectionne.observe(viewLifecycleOwner) { etudiant ->
            etudiant?.let { bindData(it) }
        }

        // Listeners pour les boutons
        binding.boutonUpdate.setOnClickListener { updateEtudiantData() }
        binding.boutonDelete.setOnClickListener { showDeleteConfirmationDialog() }

        // Observer le résultat de la mise à jour
        etudiantViewModel.updateResult.observe(viewLifecycleOwner) { success ->
            // On ne réagit que si la valeur n'est pas nulle (l'événement n'a pas été consommé)
            success?.let {
                if (it) {
                    Toast.makeText(requireContext(), "Étudiant mis à jour avec succès", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack() // Retour à la liste
                } else {
                    Toast.makeText(requireContext(), "Échec de la mise à jour", Toast.LENGTH_SHORT).show()
                }
                // Une fois l'événement traité, on le consomme dans le ViewModel
                etudiantViewModel.onUpdateResultConsumed()
            }
        }

        // Observer le résultat de la suppression
        etudiantViewModel.deleteResult.observe(viewLifecycleOwner) { success ->
            success?.let {
                if (it) {
                    Toast.makeText(requireContext(), "Étudiant supprimé avec succès", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack() // Retour à la liste
                } else {
                    Toast.makeText(requireContext(), "Échec de la suppression", Toast.LENGTH_SHORT).show()
                }
                // Consommer l'événement
                etudiantViewModel.onDeleteResultConsumed()
            }
        }
    }

    private fun bindData(etudiant: Etudiant) {
        binding.textViewCin.text = "CIN : ${etudiant.cin}"
        binding.editTextNom.setText(etudiant.nom)
        binding.editTextPrenom.setText(etudiant.prenom)
        binding.editTextMail.setText(etudiant.mail)
        binding.editTextClasse.setText(etudiant.classe)
    }

    private fun updateEtudiantData() {
        val nom = binding.editTextNom.text.toString().trim()
        val prenom = binding.editTextPrenom.text.toString().trim()
        val mail = binding.editTextMail.text.toString().trim()
        val classe = binding.editTextClasse.text.toString().trim()

        if (nom.isNotEmpty() && prenom.isNotEmpty() && mail.isNotEmpty() && classe.isNotEmpty()) {
            etudiantViewModel.etudiantSelectionne.value?.let { currentEtudiant ->
                // La méthode .copy() préserve les champs non modifiés comme le CIN et le mot de passe
                val updatedEtudiant = currentEtudiant.copy(
                    nom = nom,
                    prenom = prenom,
                    mail = mail,
                    classe = classe
                )
                etudiantViewModel.updateEtudiant(updatedEtudiant)
            }
        } else {
            Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmation de suppression")
            .setMessage("Êtes-vous sûr de vouloir supprimer cet étudiant ?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Oui") { _, _ ->
                etudiantViewModel.deleteEtudiant()
            }
            .setNegativeButton("Non", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}