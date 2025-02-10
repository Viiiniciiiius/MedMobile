package com.example.medmobile.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.Model.SelecionarP
import com.example.medmobile.R

class AdapterSelecionarP(private val selecinarP: List<SelecionarP>) :
    RecyclerView.Adapter<AdapterSelecionarP.SelecionarPViewHolder>() {

    private var selectedPosition = -1

    class SelecionarPViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val radioButtonP: RadioButton = itemView.findViewById(R.id.radioButtonP)
        val iconeView: ImageView = itemView.findViewById(R.id.fotoPImageView)
        val nomePTextView: TextView = itemView.findViewById(R.id.nomePTextView)
        val telemovelPTextView: TextView = itemView.findViewById(R.id.telemovelPTextView)
        // Removi o holder.uidP porque ele não é necessário, usamos apenas SelecionarP diretamente
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelecionarPViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_selecionar_p, parent, false)
        return SelecionarPViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelecionarPViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val profissional = selecinarP[position]

        holder.radioButtonP.isChecked = position == selectedPosition
        holder.iconeView.setImageResource(R.drawable.img_telefonia)
        holder.nomePTextView.text = profissional.nomeP
        holder.telemovelPTextView.text = profissional.telemovelP

        holder.radioButtonP.setOnClickListener {
            if (selectedPosition != position) {
                selectedPosition = position
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return selecinarP.size
    }

    // Função que retorna o profissional selecionado
    fun getSelectedItem(): SelecionarP? {
        return if (selectedPosition != -1) selecinarP[selectedPosition] else null
    }
}
