package com.example.medmobile.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.medmobile.databinding.ActivityTelapacienteBinding
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.example.medmobile.R
import com.google.firebase.auth.FirebaseAuth

class TelaPaciente : AppCompatActivity() {
    private lateinit var binding: ActivityTelapacienteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelapacienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBttFalaDr.setOnClickListener { trasicaoFalaDoutor() }

        binding.imgBttUrgencia.setOnClickListener { transicaoUrgencia() }

        binding.imgBttDuvidas.setOnClickListener { transicaoDuvidas() }

        binding.imgBttConsulta.setOnClickListener { transicaoConsulta() }

        binding.imgBttInformacoesUteis.setOnClickListener { transicaoInformacoesUteis() }

        binding.imgBttDocumentos.setOnClickListener { transicaoDocumentos() }

        binding.bttHistoricoSolicitacoesPac.setOnClickListener { transicaoHistoricoSolicitacoesPac() }

        binding.bttAdicionarProfissional.setOnClickListener { transicaoHistoricoSolicitacoesPac() }

        binding.bttLogOutPac.setOnClickListener{ DeslogarPaciente() }

    }

    private  fun trasicaoFalaDoutor(){
        val navegarFalaDoutor = Intent (this, FalaDoutorPaciente::class.java)
         startActivity(navegarFalaDoutor)
    }
    private fun transicaoUrgencia(){
        val navegarTelaUrgencia = Intent(this, UrgenciaPaciente:: class.java)
        startActivity(navegarTelaUrgencia)
    }
    private fun transicaoDuvidas(){
        val navegarTelaDuvidas = Intent(this, DuvidasPaciente:: class.java)
        startActivity(navegarTelaDuvidas)
    }
    private fun transicaoConsulta(){
        val navegarTelaConsultas = Intent(this, ConsultaPaciente:: class.java)
        startActivity(navegarTelaConsultas)
    }
    private fun transicaoInformacoesUteis(){
        val navegarInformacoesUteis = Intent ( this, InformacoesUteis::class.java)
        startActivity(navegarInformacoesUteis)
    }
    private fun transicaoDocumentos(){
        val navegarDocumentos = Intent (this, DocumentosPaciente::class.java)
        startActivity(navegarDocumentos)
    }
    private fun transicaoHistoricoSolicitacoesPac(){
        val navegarHistoricoSolicitacoesPac = Intent (this, ListaSolicitacoes::class.java)
        startActivity(navegarHistoricoSolicitacoesPac)
    }

    private fun DeslogarPaciente() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Deseja sair da conta?")
        builder.setMessage("Você precisará logar novamente.")
        builder.setIcon(R.drawable.img_logout)
        builder.setPositiveButton("Confirmar") { dialogInterface: DialogInterface, i: Int ->
            FirebaseAuth.getInstance().signOut()
            val navegarMain = Intent(this, MainActivity::class.java)
            startActivity(navegarMain)
            finish()
        }
        builder.setNegativeButton("Cancelar") { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}