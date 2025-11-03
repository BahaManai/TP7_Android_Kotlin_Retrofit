package com.isetr.menufragapp.ui.liste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.isetr.menufragapp.R
import com.isetr.menufragapp.data.Etudiant
import com.isetr.menufragapp.databinding.FragmentListeEtudiantsBinding
import com.isetr.menufragapp.viewModel.EtudiantViewModel
import kotlin.getValue

class ListeEtudiantsFragment : Fragment() {
    private var _binding: FragmentListeEtudiantsBinding? = null
    private val binding get() = _binding!!
    private lateinit var etudiantAdapter: EtudiantAdapter
    private val etudiantViewModel: EtudiantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListeEtudiantsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // val textView: TextView = binding.textListe
        // textView.text = "Liste Etudiants Fragment"
        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 🎯 L'appel de la méthode doit se faire ici !
        fillRecyclerView()
        // 2. Observation de l'état de la liste dans le ViewModel
        etudiantViewModel.etudiants.observe(viewLifecycleOwner) { nouvelleListe ->
            // Met à jour la liste dans l'Adapter et notifie le RecyclerView
            (binding.recyclerViewEtudiants.adapter as EtudiantAdapter).updateList(nouvelleListe)
            // Note : Vous devrez implémenter une méthode `updateList` dans votre EtudAdapter.
        }
    }
    private fun fillRecyclerView() {
      
        val recyclerView: RecyclerView = binding.recyclerViewEtudiants
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        etudiantAdapter = EtudiantAdapter(etudiants = mutableListOf(),
            onItemClicked = { etudiant ->
                Toast.makeText(requireContext(),
                    "etudiant selectionné ${etudiant.email}",
                    Toast.LENGTH_SHORT).show()
                // Ce bloc de code (lambda) est appelé quand un élément de la liste est cliqué
                // 1. Mettre à jour le LiveData dans le ViewModel (Partage de la donnée)
                etudiantViewModel.selectionnerEtudiant(etudiant)
                // 2. Naviguer vers le Fragment (Détail)
                // a compléter 
            }

        )
        recyclerView.adapter = etudiantAdapter
    }
}

