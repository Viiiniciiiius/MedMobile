package com.example.medmobile.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.medmobile.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var referenciaFirebase: DatabaseReference
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        referenciaFirebase = FirebaseDatabase.getInstance().reference

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bttacessPac.setOnClickListener { transicaoPacLogin() }
        binding.bttacessPro.setOnClickListener { transicaoProLogin() }
    }

    override fun onResume() {
        super.onResume()
        val currentUser = auth.currentUser
        if (currentUser != null)
            checkUserRole(currentUser.uid)
    }

        private fun checkUserRole(uid: String) {
        referenciaFirebase.child("Pacientes").child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    transicaoPac()
                } else {
                    referenciaFirebase.child("Profissionais").child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                transicaoPro()
                            } else {
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun transicaoPac() {
        val navegarTelaPac = Intent(this, TelaPaciente::class.java)
        startActivity(navegarTelaPac)
        finish()
    }

    private fun transicaoPro() {
        val navegarTelaPro = Intent(this, TelaProfissional::class.java)
        startActivity(navegarTelaPro)
        finish()
    }

    private fun transicaoPacLogin() {
        val navegarLoginPac = Intent(this, LoginPaciente::class.java)
        startActivity(navegarLoginPac)
    }

    private fun transicaoProLogin() {
        val navegarLoginPro = Intent(this, LoginProfissional::class.java)
        startActivity(navegarLoginPro)
    }
}
