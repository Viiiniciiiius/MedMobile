package com.example.medmobile.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.medmobile.Model.Solicitacao
import com.example.medmobile.databinding.ActivityMensagemPacienteBinding
import com.google.firebase.database.*

class MensagemPaciente : AppCompatActivity() {
    private lateinit var binding: ActivityMensagemPacienteBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMensagemPacienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recupera o ID da solicitação passado via Intent
        val solicitacaoId = intent.getStringExtra("SOLICITACAO_ID")
        val solicitacaoNomePro = intent.getStringExtra("SOLICITACAO_NOME_PRO")

        // Inicializa o Firebase Database
        database = FirebaseDatabase.getInstance().getReference("Solicitacoes")

        // Busca os detalhes da solicitação no Firebase
        solicitacaoId?.let { id ->
            database.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val solicitacao = snapshot.getValue(Solicitacao::class.java)

                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
}
