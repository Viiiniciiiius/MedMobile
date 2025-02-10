package com.example.medmobile.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.medmobile.Adapter.AdapterHistoricoEspecifico
import com.example.medmobile.Model.Solicitacao
import com.example.medmobile.databinding.ActivityHistoricoEspecificoBinding

class HistoricoEspecifico : AppCompatActivity() {
    private lateinit var binding: ActivityHistoricoEspecificoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHistoricoEspecificoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val  solicitacaoList = listOf(
            Solicitacao("Dr. João", "Concluído", "Consulta","", "20/09", "", "", "", "", "")
        )


        val recyclerView: RecyclerView = binding.RecyclerViewHistoricoEspecifico
        val layoutManager: LayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = AdapterHistoricoEspecifico(solicitacaoList)


        binding.bttVoltarHSP.setOnClickListener { transicaoRespostaSolicitacao() }

    }
    private fun transicaoRespostaSolicitacao(){
        val navegarResportaSolicitacao = Intent (this, ResportaSolicitacao::class.java)
        startActivity(navegarResportaSolicitacao)
    }
}