package com.example.medmobile.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medmobile.R
import com.example.medmobile.databinding.ActivityLoginProfissionalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginProfissional : AppCompatActivity() {

    private lateinit var binding: ActivityLoginProfissionalBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_profissional)

        binding = ActivityLoginProfissionalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Profissionais")

        binding.bttCadastroProfissional.setOnClickListener { transicaoCadastroProfissional() }

        binding.bttLoginProfissional.setOnClickListener { fazerLoginProfissional() }

        binding.bttLoginProVoltarMain.setOnClickListener { finish() }
    }

    private fun fazerLoginProfissional() {
        val email = binding.loginEmailProfissional.text.toString()
        val senha = binding.loginSenhaProfissional.text.toString()

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

                    // Verificar se o UID existe no nó "Profissionais"
                    if (uid != null) {
                        verificarProfissionalNoDB(uid)
                    }
                } else {
                    // Falha no login
                    Log.w("Firebase", "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "Falha no login. Verifique suas credenciais.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun verificarProfissionalNoDB(uid: String) {
        // Referência ao nó "Profissionais" e verificar se o UID existe
        database.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // UID encontrado no nó "Profissionais"
                    Log.d("Firebase", "Profissional UID encontrado no banco de dados")
                    Toast.makeText(this@LoginProfissional, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()

                    val navegarPro = Intent(this@LoginProfissional, TelaProfissional::class.java)
                    startActivity(navegarPro)
                    finish()
                } else {
                    // UID não encontrado no nó "Profissionais"
                    Log.w("Firebase", "Profissional UID não encontrado no banco de dados")
                    Toast.makeText(this@LoginProfissional, "Usuário não registrado como Profissional.", Toast.LENGTH_SHORT).show()
                    auth.signOut()  // Opcional: deslogar o usuário se não for encontrado no nó "Profissionais"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Firebase", "Erro ao verificar no banco de dados", databaseError.toException())
                Toast.makeText(this@LoginProfissional, "Erro ao verificar Profissional.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun transicaoCadastroProfissional(){
        val navegarCadastroProfissional = Intent(this, CadastroProfissional::class.java)
        startActivity(navegarCadastroProfissional)
    }
}
