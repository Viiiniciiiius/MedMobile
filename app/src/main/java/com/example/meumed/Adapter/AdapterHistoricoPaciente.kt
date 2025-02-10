package com.example.medmobile.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.Model.Solicitacao
import com.example.medmobile.R
import com.example.medmobile.Activity.MensagemPaciente

class AdapterHistoricoPaciente(
    private val solicitacaoPacienteList: List<Solicitacao>,
    private val solicitacaoKeys: List<String>  // Lista de chaves de solicitação (IDs)
) : RecyclerView.Adapter<AdapterHistoricoPaciente.SolicitacaoPacienteViewHolder>() {

    // ViewHolder para armazenar e reciclar as Views
    class SolicitacaoPacienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconeView: ImageView = itemView.findViewById(R.id.iconeHPImageView)
        val nomeProTextView: TextView = itemView.findViewById(R.id.nomeHPTextView)
        val tipoTextView: TextView = itemView.findViewById(R.id.solicitacaoHPTextView)
        val situacaoTextView: TextView = itemView.findViewById(R.id.tipoHPTextView)
        val gravidadeTextView: TextView = itemView.findViewById(R.id.gravidadeHPTextView)
        val dataTextView: TextView = itemView.findViewById(R.id.dataHPTextView)
    }

    // Infla o layout do item da lista e cria o ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolicitacaoPacienteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_historico_paciente, parent, false)
        return SolicitacaoPacienteViewHolder(view)
    }

    // Associa os dados da lista de solicitações com o ViewHolder
    override fun onBindViewHolder(holder: SolicitacaoPacienteViewHolder, position: Int) {
        val solicitacao = solicitacaoPacienteList[position]

        // Define o ícone baseado no tipo de solicitação
        holder.iconeView.setImageResource(gerarIconeView(solicitacao.tipo))

        // Preenche as Views com os dados da solicitação
        holder.nomeProTextView.text = solicitacao.nomeProfissional
        holder.tipoTextView.text = solicitacao.tipo
        holder.situacaoTextView.text = solicitacao.situacao
        holder.gravidadeTextView.text = solicitacao.gravidade
        holder.dataTextView.text = solicitacao.data

        // Adiciona o clique no item
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MensagemPaciente::class.java)
            // Passa o ID (chave) da solicitação para a próxima tela
            intent.putExtra("SOLICITACAO_ID", solicitacaoKeys[position])
            context.startActivity(intent)
        }
    }

    // Retorna a quantidade de itens na lista
    override fun getItemCount(): Int {
        return solicitacaoPacienteList.size
    }

    // Retorna o ícone apropriado com base no tipo de solicitação
    fun gerarIconeView(tipo: String): Int {
        return when(tipo) {
            "Telefonema" -> R.drawable.fala_dr_mini
            "Urgência" -> R.drawable.urgencia_mini
            "Dúvida" -> R.drawable.duvida_mini
            "Consulta" -> R.drawable.consulta_mini
            "Documento" -> R.drawable.documento_mini
            else -> R.drawable.img_telefonia  // Ícone padrão
        }
    }
}
