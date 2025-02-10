package com.example.medmobile.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.Model.Solicitacao
import com.example.medmobile.R

class AdapterListaDeTarefas(private val solicitacaoListaDeTarefas: List<Solicitacao>) :
    RecyclerView.Adapter<AdapterListaDeTarefas.ListaDeTarefasViewHolder>() {

    class ListaDeTarefasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconeView: ImageView = itemView.findViewById(R.id.iconeImageView2)
        val nomePacTextView: TextView = itemView.findViewById(R.id.nomePacTextView)
        val tipoTextView: TextView = itemView.findViewById(R.id.tipoTextView2)
        val situacaoTextView: TextView = itemView.findViewById(R.id.situacaoTextView2)
        val dataTextView: TextView = itemView.findViewById(R.id.dataTextView2)
        val gravidadeTextView: TextView = itemView.findViewById(R.id.gravidadeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaDeTarefasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_lista_de_tarefas, parent, false)
        return ListaDeTarefasViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListaDeTarefasViewHolder, position: Int) {
        val solicitacao = solicitacaoListaDeTarefas[position]
        holder.iconeView.setImageResource(gerarIconeView(solicitacao.tipo))
        holder.nomePacTextView.text = solicitacao.nomePaciente
        holder.tipoTextView.text = solicitacao.tipo
        holder.situacaoTextView.text = solicitacao.situacao
        holder.dataTextView.text = solicitacao.data
        holder.gravidadeTextView.text = solicitacao.gravidade
    }

    override fun getItemCount(): Int {
        return solicitacaoListaDeTarefas.size
    }

    fun gerarIconeView(tipo: String): Int {
        return when(tipo){
            "Telefonema" -> R.drawable.fala_dr_mini
            "Urgência" -> R.drawable.urgencia_mini
            "Dúvida" -> R.drawable.duvida_mini
            "Consulta" -> R.drawable.consulta_mini
            "Documento" -> R.drawable.documento_mini
            else -> R.drawable.img_telefonia
        }
    }
}