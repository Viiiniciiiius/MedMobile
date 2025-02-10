package com.example.medmobile.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.Adapter.AdapterSelecionarP
import com.example.medmobile.Model.SelecionarP
import com.example.medmobile.Model.Solicitacao
import com.example.medmobile.databinding.ActivityDocumentosPacienteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class DocumentosPaciente : AppCompatActivity() {
    private lateinit var binding: ActivityDocumentosPacienteBinding
    private lateinit var adapter: AdapterSelecionarP
    private var selectedProfissional: SelecionarP? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDocumentosPacienteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Configuração do RecyclerView
        val recyclerViewSelecionarP: RecyclerView = binding.RecyclerViewSelecionarProDoc
        val layoutManagerListaDeTarefas: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewSelecionarP.layoutManager = layoutManagerListaDeTarefas

        // Recupera o UID do paciente autenticado e lista os profissionais associados
        val currentUser = FirebaseAuth.getInstance().currentUser
        val pacienteUid = currentUser?.uid

        pacienteUid?.let { uid ->
            val databaseRef = FirebaseDatabase.getInstance().getReference("Pacientes")
            databaseRef.child(uid).child("uidProfissionais").get().addOnSuccessListener { dataSnapshot ->
                val profissionaisUids = dataSnapshot.getValue(String::class.java)?.split(" @$ ") ?: emptyList()
                buscarDetalhesProfissionais(profissionaisUids, recyclerViewSelecionarP)
            }.addOnFailureListener {
                Toast.makeText(this, "Erro ao recuperar dados dos profissionais", Toast.LENGTH_SHORT).show()
            }
        }

        binding.bttListaDoc.setOnClickListener { transicaoListaDoc() }
        binding.bttVoltarDoc.setOnClickListener { finish() }
        binding.bttEnviarDoc.setOnClickListener { enviarSolicitacao(pacienteUid) }
    }

    private fun buscarDetalhesProfissionais(uids: List<String>, recyclerView: RecyclerView) {
        val profissionaisRef = FirebaseDatabase.getInstance().getReference("Profissionais")
        val profissionaisList = mutableListOf<SelecionarP>()

        uids.forEach { uid ->
            profissionaisRef.child(uid).get().addOnSuccessListener { dataSnapshot ->
                val nome = dataSnapshot.child("nome").getValue(String::class.java) ?: ""
                val telefone = dataSnapshot.child("telefone").getValue(String::class.java) ?: ""

                profissionaisList.add(SelecionarP(nome, telefone, uid))
                atualizarRecyclerView(profissionaisList, recyclerView)
            }.addOnFailureListener {
                Toast.makeText(this, "Erro ao recuperar dados do profissional", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun atualizarRecyclerView(profissionaisList: List<SelecionarP>, recyclerView: RecyclerView) {
        adapter = AdapterSelecionarP(profissionaisList)
        recyclerView.adapter = adapter
    }

    private fun enviarSolicitacao(pacienteUid: String?) {
        val textoPac = binding.editTextDoc.text.toString().trim()

        // Obter o profissional selecionado
        val selectedProfissional = adapter.getSelectedItem()

        if (textoPac.isEmpty() || selectedProfissional == null) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }
        // Obter o nome do paciente
        pacienteUid?.let { uid ->
            val pacienteRef = FirebaseDatabase.getInstance().getReference("Pacientes").child(uid)
            pacienteRef.child("nome").get().addOnSuccessListener { dataSnapshot ->
                val nomePaciente = dataSnapshot.getValue(String::class.java) ?: ""

                val solicitacaoId = FirebaseDatabase.getInstance().getReference("Solicitacoes").push().key
                val dataAtual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

                // Criar a solicitação
                val solicitacao = Solicitacao(
                    uidPaciente = pacienteUid,
                    uidProfissional = selectedProfissional.uidP,  // Usar o UID do profissional selecionado
                    textoPac = textoPac,
                    textoPro = "",
                    nomeProfissional = selectedProfissional.nomeP,
                    nomePaciente = nomePaciente,  // Adicionar o nome do paciente aqui
                    situacao = "Pendente",
                    tipo = "Documento",
                    data = dataAtual,
                    gravidade = "" // Se não houver gravidade, pode ser deixado vazio ou removido
                )

                solicitacaoId?.let { id ->
                    FirebaseDatabase.getInstance().getReference("Solicitacoes")
                        .child(id)
                        .setValue(solicitacao)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Solicitação enviada com sucesso", Toast.LENGTH_SHORT).show()
                            transicaoEnviarDoc()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Erro ao enviar solicitação", Toast.LENGTH_SHORT).show()
                        }
                }

            }.addOnFailureListener {
                Toast.makeText(this, "Erro ao recuperar o nome do paciente", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun transicaoListaDoc() {
        val navegarListaDoc = Intent(this, ListaSolicitacoes::class.java)
        startActivity(navegarListaDoc)
    }

    private fun transicaoEnviarDoc() {
        val navegarEnviarDoc = Intent(this, ListaSolicitacoes::class.java)
        startActivity(navegarEnviarDoc)
        finish()
    }
}
