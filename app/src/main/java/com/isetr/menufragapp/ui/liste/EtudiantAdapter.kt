package com.isetr.menufragapp.ui.liste
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isetr.menufragapp.data.Etudiant
import com.isetr.menufragapp.databinding.ItemEtudiantBinding

class EtudiantAdapter(
    private val etudiants: MutableList<Etudiant>,
    private val onItemClicked: (Etudiant) -> Unit
) : RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtudiantViewHolder {
        val binding = ItemEtudiantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EtudiantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EtudiantViewHolder, position: Int) {
        val etudiant = etudiants.get(position)
        holder.bind(etudiant)
        holder.itemView.setOnClickListener {
            onItemClicked(etudiant)
        }
    }

    override fun getItemCount() = etudiants.size
    fun updateList(newList: List<Etudiant>) {
        this.etudiants.clear()
        this.etudiants.addAll(newList)
        notifyDataSetChanged()
    }
    inner class EtudiantViewHolder(private val binding: ItemEtudiantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(etudiant: Etudiant) {
            binding.textViewMail.text = " ${etudiant.mail}"
            binding.textViewClasse.text = "Classe : ${etudiant.classe}"
        }
    }
}




// DiffUtil optimise la mise à jour des listes
