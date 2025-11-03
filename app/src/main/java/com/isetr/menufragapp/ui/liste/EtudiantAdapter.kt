package com.isetr.menufragapp.ui.liste
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isetr.menufragapp.data.Etudiant
import com.isetr.menufragapp.databinding.ItemEtudiantBinding

class EtudiantAdapter(
    private val etudiants: MutableList<Etudiant>,
    // 🔑 AJOUT CRUCIAL : Une fonction qui prend un Etudiant et ne retourne rien
    private val onItemClicked: (Etudiant) -> Unit
) : RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder>(){
    //RecyclerView.Adapter<Etudiant, EtudiantAdapter.EtudiantViewHolder>()
    // Crée le ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtudiantViewHolder {
        val binding = ItemEtudiantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EtudiantViewHolder(binding)
    }

    // Lie les données à la vue
    override fun onBindViewHolder(holder: EtudiantViewHolder, position: Int) {
        val etudiant = etudiants.get(position)
        holder.bind(etudiant)
        // 🔑 NOUVELLE LOGIQUE DE CLIC
        holder.itemView.setOnClickListener {
            // Quand l'utilisateur clique, nous appelons la fonction (lambda)
            // qui nous a été passée par le Fragment, lui transmettant l'objet Etudiant.
            onItemClicked(etudiant)
        }
    }

    override fun getItemCount() = etudiants.size
    fun updateList(newList: List<Etudiant>) {
        // Supposons que 'etudiants' est le MutableList interne de votre Adapter
        this.etudiants.clear()
        this.etudiants.addAll(newList)
        notifyDataSetChanged()
    }
    // Le ViewHolder contient les références des vues d'une ligne
    inner class EtudiantViewHolder(private val binding: ItemEtudiantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(etudiant: Etudiant) {
            // Remplissage des vues avec les données de l'étudiant
            binding.textViewMail.text = " ${etudiant.email}"
            binding.textViewClasse.text = "Classe : ${etudiant.classe}"
        }
    }
}




// DiffUtil optimise la mise à jour des listes
