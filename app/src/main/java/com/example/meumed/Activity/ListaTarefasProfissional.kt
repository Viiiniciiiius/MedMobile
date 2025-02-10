package com.example.medmobile.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.medmobile.Adapter.AdapterListaDeTarefas
import com.example.medmobile.Model.Solicitacao
import com.example.medmobile.databinding.ActivityListaTarefasProfissionalBinding

class ListaTarefasProfissional : AppCompatActivity() {
    private lateinit var binding: ActivityListaTarefasProfissionalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaTarefasProfissionalBinding.inflate(layoutInflater)
        setContentView(binding.root)

//dados
        val  solicitacaoList = listOf(
            Solicitacao("Dr. João", "Concluído", "Consulta", "20/09", "", "", "", "", "")
        )

        //configuracao do recyclerView
        val recyclerViewListaDeTarefas :RecyclerView = binding.RecyclerViewListaDeTarefas
        val layoutManagerListaDeTarefas :LayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewListaDeTarefas.layoutManager = layoutManagerListaDeTarefas
        recyclerViewListaDeTarefas.adapter = AdapterListaDeTarefas(solicitacaoList)

        //configuracao do recyclerView
        val recyclerViewSelecionarPaciente :RecyclerView = binding.RecyclerViewSelecionarPaciente
        val layoutManagerSelecionarPaciente :LayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewSelecionarPaciente.layoutManager = layoutManagerSelecionarPaciente
        recyclerViewSelecionarPaciente.adapter = AdapterListaDeTarefas(solicitacaoList)


        binding.bttHistoricoSolicitacoes.setOnClickListener { transicaoHistoricoSolicitacoes() }

        binding.bttVoltarTelaProfissional.setOnClickListener { finish() }

    }
    private fun transicaoHistoricoSolicitacoes(){
        val navegarHistoricoSolicitacoes = Intent (this, HistoricoEspecifico::class.java)
        startActivity(navegarHistoricoSolicitacoes)
    }

    private fun transicaoRespostaSolicitacao(){
        val navegarRespostaSolicitacao = Intent (this, ResportaSolicitacao::class.java)
        startActivity(navegarRespostaSolicitacao)
    }
}