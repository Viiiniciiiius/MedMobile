package com.example.medmobile.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.medmobile.databinding.ActivityResportaSolicitacaoBinding

class ResportaSolicitacao : AppCompatActivity() {
    private lateinit var binding: ActivityResportaSolicitacaoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResportaSolicitacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bttVoltarListaProfissional.setOnClickListener { finish()}
        
        binding.bttEnviarRespostaProfissional.setOnClickListener { trasicaoEnviarListaProfissional() }

    }
    private fun trasicaoEnviarListaProfissional(){
        val navegarVoltarListaProfissional = Intent (this, ListaTarefasProfissional::class.java)
        startActivity(navegarVoltarListaProfissional)
    }
}