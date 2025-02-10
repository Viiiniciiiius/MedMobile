package com.example.medmobile.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.Adapter.AdapterHistoricoPaciente
import com.example.medmobile.Model.Solicitacao
import com.example.medmobile.databinding.ActivityListaSolicitacoesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ListaSolicitacoes : AppCompatActivity() {
    private lateinit var binding: ActivityListaSolicitacoesBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaSolicitacoesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura o botão de voltar
        binding.bttVoltarLista.setOnClickListener { finish() }

        // Configuração do RecyclerView
        val recyclerView: RecyclerView = binding.RecyclerViewHistoricoPac
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager

        // Inicializa o Firebase Database
        database = FirebaseDatabase.getInstance().getReference("Solicitacoes")

        // Recupera o UID do paciente logado
        val uidPaciente = FirebaseAuth.getInstance().currentUser?.uid

        // Verifica se o UID do paciente não é nulo
        uidPaciente?.let { uid ->
            // Faz a consulta no Firebase para buscar as solicitações do paciente logado
            database.orderByChild("uidPaciente").equalTo(uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val solicitacoesList = mutableListOf<Solicitacao>()
                        val solicitacaoKeys = mutableListOf<String>()  // Para armazenar as chaves

                        for (data in snapshot.children) {
                            val solicitacao = data.getValue(Solicitacao::class.java)
                            solicitacao?.let {
                                solicitacoesList.add(it)
                                solicitacaoKeys.add(data.key!!)  // Armazena a chave da solicitação
                            }
                        }

                        // Atualiza o Adapter com a lista de solicitações e as chaves
                        val adapter = AdapterHistoricoPaciente(solicitacoesList, solicitacaoKeys)
                        recyclerView.adapter = adapter
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Lide com o erro aqui, ex.: mostrar uma mensagem ao usuário
                    }
                })
        }
    }
}
