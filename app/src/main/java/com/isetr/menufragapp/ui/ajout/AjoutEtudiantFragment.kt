package com.isetr.menufragapp.ui.ajout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.isetr.menufragapp.data.Etudiant
import com.isetr.menufragapp.databinding.FragmentAjoutEtudiantBinding
import com.isetr.menufragapp.viewModel.EtudiantViewModel

class AjoutEtudiantFragment : Fragment() {

    private var _binding: FragmentAjoutEtudiantBinding? = null
    private val binding get() = _binding!!

    private val etudiantViewModel: EtudiantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAjoutEtudiantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.boutonAjouter.setOnClickListener {
            ajouterEtudiant()
        }

        // Observer le résultat de l'ajout
        etudiantViewModel.addResult.observe(viewLifecycleOwner) { etudiant ->
            if (etudiant != null) {
                Toast.makeText(requireContext(), "Étudiant ajouté avec succès !", Toast.LENGTH_SHORT).show()
                viderChamps()
            }
        }

        // Observer les messages d'erreur
        etudiantViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if(error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun ajouterEtudiant() {
        val cinString = binding.editTextCin.text.toString().trim()
        val nom = binding.editTextNom.text.toString().trim()
        val prenom = binding.editTextPrenom.text.toString().trim()
        val mail = binding.editTextMail.text.toString().trim()
        val password = binding.editTextPassword.text.toString()
        val classe = binding.editTextClasse.text.toString().trim()

        if (cinString.isNotEmpty() && nom.isNotEmpty() && prenom.isNotEmpty() && mail.isNotEmpty() && password.isNotEmpty() && classe.isNotEmpty()) {
            try {
                val cin = cinString.toInt()
                val nouvelEtudiant = Etudiant(cin, nom, prenom, mail, password, classe)
                etudiantViewModel.addNewEtudiant(nouvelEtudiant)
            } catch (e: NumberFormatException) {
                Toast.makeText(requireContext(), "Le CIN doit être un nombre valide", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
        }
    }

    private fun viderChamps() {
        binding.editTextCin.text?.clear()
        binding.editTextNom.text?.clear()
        binding.editTextPrenom.text?.clear()
        binding.editTextMail.text?.clear()
        binding.editTextPassword.text?.clear()
        binding.editTextClasse.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}