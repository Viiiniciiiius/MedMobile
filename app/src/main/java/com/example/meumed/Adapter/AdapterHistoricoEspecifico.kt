package com.example.medmobile.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.Model.Solicitacao
import com.example.medmobile.R

class AdapterHistoricoEspecifico (private val historicoEspecificoList: List<Solicitacao>) :
    RecyclerView.Adapter<AdapterHistoricoEspecifico.HistoricoEspecificoViewHolder>() {

    class HistoricoEspecificoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconeHEView: ImageView = itemView.findViewById(R.id.iconeHEImageView)
        val tipoHETextView: TextView = itemView.findViewById(R.id.tipoHETextView)
        val situacaoHETextView: TextView = itemView.findViewById(R.id.situacaoHETextView)
        val gravidadeHETextView: TextView = itemView.findViewById(R.id.gravidadeHETextView)
        val dataHETextView: TextView = itemView.findViewById(R.id.dataHETextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoEspecificoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_historico_especifico, parent, false)
        return HistoricoEspecificoViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoricoEspecificoViewHolder, position: Int) {
        val solicitacao = historicoEspecificoList[position]
        holder.iconeHEView.setImageResource(gerarIconeView(solicitacao.tipo))
        holder.tipoHETextView.text = solicitacao.tipo
        holder.situacaoHETextView.text = solicitacao.situacao
        holder.gravidadeHETextView.text = solicitacao.gravidade
        holder.dataHETextView.text = solicitacao.data
    }

    override fun getItemCount(): Int {
        return historicoEspecificoList.size
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