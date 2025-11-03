package com.isetr.menufragapp.ui.ajout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.isetr.menufragapp.data.Etudiant
import com.isetr.menufragapp.databinding.FragmentAjoutEtudiantBinding
import com.isetr.menufragapp.viewModel.EtudiantViewModel

class AjoutEtudiantFragment : Fragment() {

    private var _binding: FragmentAjoutEtudiantBinding? = null
    // 🎯 Important : utilise activityViewModels() pour partager l'instance avec les autres fragments
    private val etudiantViewModel: EtudiantViewModel by activityViewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       // val galleryViewModel =
         //   ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentAjoutEtudiantBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Sans View Model
        //val textView: TextView = binding.textGallery
        //textView.text="Ajout Etudiant"
        // Avec View Model
        // galleryViewModel.text.observe(viewLifecycleOwner) {
           // textView.text = it
       // }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.boutonAjouter.setOnClickListener {
            ajouterEtudiant()
        }
    }

    private fun ajouterEtudiant() {
        val id = binding.editTextId.text.toString()
        val filiere = binding.editTextClasse.text.toString()
        val email = binding.editTextMail.text.toString()

        if (id.isNotBlank() && filiere.isNotBlank()) {
            val nouvelEtudiant = Etudiant(id, filiere, email)

            // 1. Appelle la fonction du ViewModel pour modifier les données
            etudiantViewModel.addEtudiant(nouvelEtudiant)
            // (Optionnel : afficher un Toast de succès)
            Toast.makeText(requireContext(), "Étudiant ajouté : $id", Toast.LENGTH_SHORT).show()
            binding.editTextClasse.text?.clear()
            binding.editTextMail.text?.clear()
            binding.editTextId.text?.clear()
            binding.inputLayoutId.error = null // Nettoyer l'erreur
        } else {
            Toast.makeText(requireContext(), "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}