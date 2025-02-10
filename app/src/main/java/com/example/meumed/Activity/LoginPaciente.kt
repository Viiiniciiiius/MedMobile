package com.example.medmobile.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medmobile.R
import com.example.medmobile.databinding.ActivityLoginPacienteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginPaciente : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPacienteBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_paciente)

        binding = ActivityLoginPacienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Pacientes")

        binding.bttCadastroPaciente.setOnClickListener { transicaoCadastroPaciente() }

        binding.bttLoginPaciente.setOnClickListener { fazerLoginPaciente() }

        binding.bttLoginPacVoltarMain.setOnClickListener { finish() }
    }

    private fun fazerLoginPaciente() {
        val email = binding.loginEmailPaciente.text.toString()
        val senha = binding.loginSenhaPaciente.text.toString()

        // Verificação básica dos dados
        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Autenticação com Firebase
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login bem-sucedido, obter UID do usuário
                    val user = auth.currentUser
                    val uid = user?.uid

                    // Verificar se o UID existe no nó "pacientes"
                    if (uid != null) {
                        verificarPacienteNoDB(uid)
                    }
                } else {
                    // Falha no login
                    Log.w("Firebase", "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "Falha no login. Verifique suas credenciais.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun verificarPacienteNoDB(uid: String) {
        // Referência ao nó "pacientes" e verificar se o UID existe
        database.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // UID encontrado no nó "pacientes"
                    Log.d("Firebase", "Paciente UID encontrado no banco de dados")
                    Toast.makeText(this@LoginPaciente, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()

                    // Navegar para a tela principal
                    val navegarPac = Intent(this@LoginPaciente, TelaPaciente::class.java)
                    startActivity(navegarPac)
                    finish()
                } else {
                    // UID não encontrado no nó "pacientes"
                    Log.w("Firebase", "Paciente UID não encontrado no banco de dados")
                    Toast.makeText(this@LoginPaciente, "Usuário não registrado como paciente.", Toast.LENGTH_SHORT).show()
                    auth.signOut()  // Opcional: deslogar o usuário se não for encontrado no nó "pacientes"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Firebase", "Erro ao verificar no banco de dados", databaseError.toException())
                Toast.makeText(this@LoginPaciente, "Erro ao verificar paciente.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun transicaoCadastroPaciente(){
        val navegarCadastroPaciente = Intent(this, CadastroPaciente::class.java)
        startActivity(navegarCadastroPaciente)
    }
}
