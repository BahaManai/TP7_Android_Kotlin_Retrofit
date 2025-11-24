package com.isetr.menufragapp.ui.liste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.isetr.menufragapp.R
import com.isetr.menufragapp.databinding.FragmentListeEtudiantsBinding
import com.isetr.menufragapp.viewModel.EtudiantViewModel

class ListeEtudiantsFragment : Fragment() {

    private var _binding: FragmentListeEtudiantsBinding? = null
    private val binding get() = _binding!!

    private val etudiantViewModel: EtudiantViewModel by activityViewModels()
    private lateinit var etudiantAdapter: EtudiantAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListeEtudiantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        etudiantViewModel.etudiants.observe(viewLifecycleOwner) { etudiants ->
            etudiants?.let { etudiantAdapter.updateList(it) }
        }

        etudiantViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                binding.textError.text = error
                binding.textError.visibility = View.VISIBLE
                binding.recyclerViewEtudiants.visibility = View.GONE
            } else {
                binding.textError.visibility = View.GONE
                binding.recyclerViewEtudiants.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView() {
        etudiantAdapter = EtudiantAdapter(mutableListOf()) { etudiant ->
            etudiantViewModel.selectionnerEtudiant(etudiant)
            findNavController().navigate(R.id.action_liste_to_detail)
        }

        binding.recyclerViewEtudiants.apply {
            adapter = etudiantAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}