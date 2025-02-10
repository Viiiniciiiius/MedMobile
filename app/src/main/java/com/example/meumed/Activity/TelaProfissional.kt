package com.example.medmobile.Activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.medmobile.R
import com.example.medmobile.databinding.ActivityTelaprofissionalBinding
import com.google.firebase.auth.FirebaseAuth

class TelaProfissional : AppCompatActivity() {

    private lateinit var binding: ActivityTelaprofissionalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaprofissionalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bttConfiguracoesProfissional.setOnClickListener { transicaoConfiguracoesProfissional() }

        binding.bttListaTarefas.setOnClickListener { transicaoListaTarefas() }

        binding.bttLogOutPro.setOnClickListener { DeslogarProfissional() }

    }
    private fun transicaoConfiguracoesProfissional(){
        val navegarConfiguracoesProfissional = Intent (this, ConfiguracoesProfissional::class.java)
        startActivity(navegarConfiguracoesProfissional)
    }

    private fun transicaoListaTarefas(){
        val navegarListaTarefas = Intent (this, ListaTarefasProfissional::class.java)
        startActivity(navegarListaTarefas)
    }

    private fun DeslogarProfissional() {
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