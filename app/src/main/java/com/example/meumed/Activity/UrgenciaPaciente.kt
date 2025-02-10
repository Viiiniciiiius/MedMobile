package com.example.medmobile.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.Adapter.AdapterSelecionarP
import com.example.medmobile.Model.SelecionarP
import com.example.medmobile.Model.Solicitacao
import com.example.medmobile.databinding.ActivityUrgenciaPacienteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class UrgenciaPaciente : AppCompatActivity() {
    private lateinit var binding: ActivityUrgenciaPacienteBinding
    private lateinit var recyclerViewSelecionarP: RecyclerView
    private var selectedProfissional: SelecionarP? = null
    private lateinit var adapter: AdapterSelecionarP // Adiciona o adapter como uma variável global

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUrgenciaPacienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuração do RecyclerView
        recyclerViewSelecionarP = binding.RecyclerViewSelecionarProU
        val layoutManagerListaDeTarefas: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewSelecionarP.layoutManager = layoutManagerListaDeTarefas

        // Recupera o UID do paciente autenticado e lista os profissionais associados
        val currentUser = FirebaseAuth.getInstance().currentUser
        val pacienteUid = currentUser?.uid

        pacienteUid?.let { uid ->
            val databaseRef = FirebaseDatabase.getInstance().getReference("Pacientes")
            databaseRef.child(uid).child("uidProfissionais").get().addOnSuccessListener { dataSnapshot ->
                val profissionaisUids = dataSnapshot.getValue(String::class.java)?.split(" @$ ") ?: emptyList()
                buscarDetalhesProfissionais(profissionaisUids)
            }.addOnFailureListener {
                Toast.makeText(this, "Erro ao recuperar dados dos profissionais", Toast.LENGTH_SHORT).show()
            }
        }

        binding.bttVoltar.setOnClickListener { finish() }
        binding.bttEnviar.setOnClickListener { enviarSolicitacao(pacienteUid) }
        binding.bttListaU.setOnClickListener { acompanharUrgencias() }
    }

    private fun buscarDetalhesProfissionais(uids: List<String>) {
        val profissionaisRef = FirebaseDatabase.getInstance().getReference("Profissionais")
        val profissionaisList = mutableListOf<SelecionarP>()

        uids.forEach { uid ->
            profissionaisRef.child(uid).get().addOnSuccessListener { dataSnapshot ->
                val nome = dataSnapshot.child("nome").getValue(String::class.java) ?: ""
                val telefone = dataSnapshot.child("telefone").getValue(String::class.java) ?: ""

                profissionaisList.add(SelecionarP(nome, telefone, uid))
                atualizarRecyclerView(profissionaisList)
            }.addOnFailureListener {
                Toast.makeText(this, "Erro ao recuperar dados do profissional", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun atualizarRecyclerView(profissionaisList: List<SelecionarP>) {
        adapter = AdapterSelecionarP(profissionaisList)
        recyclerViewSelecionarP.adapter = adapter
    }

    private fun enviarSolicitacao(pacienteUid: String?) {
        val textoPac = binding.editTextText.text.toString().trim()
        val gravidade = when {
            binding.radioButtonU.isChecked -> "Nível 1"
            binding.radioButtonU2.isChecked -> "Nível 2"
            binding.radioButtonU3.isChecked -> "Nível 3"
            binding.radioButtonU4.isChecked -> "Nível 4"
            binding.radioButtonU5.isChecked -> "Nível 5"
            binding.radioButtonU6.isChecked -> "Nível 6"
            else -> ""
        }

        // Obter o profissional selecionado
        val selectedProfissional = adapter.getSelectedItem()

        if (textoPac.isEmpty() || gravidade.isEmpty() || selectedProfissional == null) {
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
                    tipo = "Urgência",
                    data = dataAtual,
                    gravidade = "" // Se não houver gravidade, pode ser deixado vazio ou removido
                )

                solicitacaoId?.let { id ->
                    FirebaseDatabase.getInstance().getReference("Solicitacoes")
                        .child(id)
                        .setValue(solicitacao)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Solicitação enviada com sucesso", Toast.LENGTH_SHORT).show()
                            acompanharUrgencias()
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

    private fun acompanharUrgencias() {
        val acompUrge = Intent(this, ListaSolicitacoes::class.java)
        startActivity(acompUrge)
        finish()
    }
}
