package com.example.medmobile.Activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.medmobile.databinding.ActivityCadastroProfissionalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.HashMap

class CadastroProfissional : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroProfissionalBinding
    private var nomeCompletoValidoProfissional = false
    private var dataNascimentoValidoProfissional = false
    private var enderecoValidoProfissional = false
    private var codigoPostalValidoProfissional = false
    private var cidadeValidoProfissional = false
    private var emailValidoProfissional = true
    private var telemovelValidoProfissional = false
    private var telemovelValido2Profissional = true
    private var senhaValido1Profissional = false
    private var senhaValido2Profissional = false

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroProfissionalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()

        val nomeCompletoPattern = Regex("^[A-Za-zÀ-ÖØ-öø-ÿ\\s]{3,90}$")
        val dataNascimentoPattern = Regex("^([0-2][0-9]|3[01])/(0[1-9]|1[0-2])/(19[0-9]{2}|20[01][0-9]|202[0-3])$")
        val emailPattern = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        val telemovelPattern = Regex("^\\(?([0-9]){2}\\)?( )?([0-9])?( )?[0-9]{4}-?[0-9]{4}$")
        val enderecoPattern = Regex("^[\\p{L}\\s.'-]+,?\\s?\\d+([.,ºª\\s\\w]*)?$")
        val codigoPostalPattern = Regex("^\\d{4}-\\d{3}(\\s[\\p{L}\\s]+)?$")
        val cidadePattern = Regex("^[\\p{L}\\s'-]+$")
        val senhaPattern = Regex("^(?=.*[\\w])[\\w\\d!@#$%^&*()_+=-]{8,}$")


        binding.editTextNomeCompletoProfissional.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextNomeCompletoProfissional.addTextChangedListener {
                nomeCompletoValidoProfissional = nomeCompletoPattern.matches(binding.editTextNomeCompletoProfissional.text.toString())
                if (nomeCompletoValidoProfissional) {
                    binding.layoutNomeCompletoProfissional.error = null
                    nomeCompletoValidoProfissional = true
                } else
                    nomeCompletoValidoProfissional = false
            }
            if (!hasFocus) {
                nomeCompletoValidoProfissional = nomeCompletoPattern.matches(binding.editTextNomeCompletoProfissional.text.toString())
                if (!nomeCompletoValidoProfissional)
                    binding.layoutNomeCompletoProfissional.error = "Nome Inválido"
            }
        }
/////////////////////////data de nascimento

        binding.editTextDataNascimentoProfissional.addTextChangedListener(object : TextWatcher {
            var isUpdating = false
            val datePattern = "##/##/####"

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(editable: Editable?) {
                if (isUpdating) return

                isUpdating = true
                val input = editable.toString().replace("\\D".toRegex(), "")

                val formatted = StringBuilder()
                var i = 0

                while (i < input.length && i < datePattern.length) {
                    formatted.append(input[i])
                    if (i == 1 || i == 3) {
                        formatted.append('/')
                    }
                    i++
                }
                binding.editTextDataNascimentoProfissional.setText(formatted.toString())
                binding.editTextDataNascimentoProfissional.setSelection(formatted.length)
                isUpdating = false

                dataNascimentoValidoProfissional = dataNascimentoPattern.matches(binding.editTextDataNascimentoProfissional.text.toString())
                if (dataNascimentoValidoProfissional) {
                    binding.layoutDataNacimentoProfissional.error = null
                    dataNascimentoValidoProfissional = true
                } else
                    dataNascimentoValidoProfissional = false
            }
        })
        binding.editTextDataNascimentoProfissional.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                dataNascimentoValidoProfissional = dataNascimentoPattern.matches(binding.editTextDataNascimentoProfissional.text.toString())
                if (!dataNascimentoValidoProfissional)
                    binding.layoutDataNacimentoProfissional.error = "Nome Inválido"
            }
        }
//ENDERECO
        binding.editTextEnderecoProfissional.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextEnderecoProfissional.addTextChangedListener {
                enderecoValidoProfissional = enderecoPattern.matches(binding.editTextEnderecoProfissional.text.toString())
                if (enderecoValidoProfissional) {
                    binding.layoutEnderecoProfissional.error = null
                    enderecoValidoProfissional = true
                } else
                    enderecoValidoProfissional = false
            }
            if (!hasFocus) {
                enderecoValidoProfissional = enderecoPattern.matches(binding.editTextEnderecoProfissional.text.toString())
                if (!enderecoValidoProfissional)
                    binding.layoutEnderecoProfissional.error = "Endereço Inválido"
            }
        }
        /////////codigo postal
        binding.editTextCodigoPostalProfissional.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextCodigoPostalProfissional.addTextChangedListener {
                codigoPostalValidoProfissional = codigoPostalPattern.matches(binding.editTextCodigoPostalProfissional.text.toString())
                if (codigoPostalValidoProfissional) {
                    binding.layoutCodigoPostalProfissional.error = null
                    codigoPostalValidoProfissional = true
                } else
                    codigoPostalValidoProfissional = false
            }
            if (!hasFocus) {
                codigoPostalValidoProfissional = codigoPostalPattern.matches(binding.editTextCodigoPostalProfissional.text.toString())
                if (!codigoPostalValidoProfissional)
                    binding.layoutCodigoPostalProfissional.error = "Código Postal Inválido"
            }
        }


        ////////////////cidade
        binding.editTextCidadeProfissional.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextCidadeProfissional.addTextChangedListener {
                cidadeValidoProfissional = cidadePattern.matches(binding.editTextCidadeProfissional.text.toString())
                if (cidadeValidoProfissional) {
                    binding.layoutCidadeProfissional.error = null
                    cidadeValidoProfissional = true
                } else
                    cidadeValidoProfissional = false
            }
            if (!hasFocus) {
                cidadeValidoProfissional = cidadePattern.matches(binding.editTextCidadeProfissional.text.toString())
                if (!cidadeValidoProfissional)
                    binding.layoutCidadeProfissional.error = "Cidade Inválida"
            }
        }

        /////////telemovel
        binding.editTextTelemovel1Profissional.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextTelemovel1Profissional.addTextChangedListener {
                telemovelValidoProfissional = telemovelPattern.matches(binding.editTextTelemovel1Profissional.text.toString())
                if (telemovelValidoProfissional || binding.editTextTelemovel1Profissional.text.toString().isEmpty()) {
                    binding.layoutTelemovel1Profissional.error = null
                    telemovelValidoProfissional = true
                } else
                    telemovelValidoProfissional = false
            }
            if (!hasFocus) {
                telemovelValidoProfissional =
                    telemovelPattern.matches(binding.editTextTelemovel1Profissional.text.toString())
                if (!telemovelValidoProfissional && binding.editTextTelemovel1Profissional.text.toString().isNotEmpty()){
                    binding.layoutTelemovel1Profissional.error = "Telemóvel Inválido"
                }else
                    telemovelValidoProfissional = true
            }
        }
/////////////////////////tele2
        binding.editTextTelemovel2Profissional.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextTelemovel2Profissional.addTextChangedListener {
                telemovelValido2Profissional = telemovelPattern.matches(binding.editTextTelemovel2Profissional.text.toString())
                if (telemovelValido2Profissional || binding.editTextTelemovel2Profissional.text.toString().isEmpty()) {
                    binding.layoutTelemovel2Profissional.error = null
                    telemovelValido2Profissional = true
                } else
                    telemovelValido2Profissional = false
            }
            if (!hasFocus) {
                telemovelValido2Profissional =
                    telemovelPattern.matches(binding.editTextTelemovel2Profissional.text.toString())
                if (!telemovelValido2Profissional && binding.editTextTelemovel2Profissional.text.toString().isNotEmpty()){
                    binding.layoutTelemovel2Profissional.error = "Telemóvel Inválido"
                }else
                    telemovelValido2Profissional = true
            }
        }
////////////////////////email
        binding.editTextEmailProfissional.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextEmailProfissional.addTextChangedListener {
                emailValidoProfissional = emailPattern.matches(binding.editTextEmailProfissional.text.toString())
                if (emailValidoProfissional) {
                    binding.layoutEmailProfissional.error = null
                    emailValidoProfissional = true
                } else
                    emailValidoProfissional = false
            }
            if (!hasFocus) {
                emailValidoProfissional = emailPattern.matches(binding.editTextEmailProfissional.text.toString())
                if (!emailValidoProfissional)
                    binding.layoutEmailProfissional.error = "Email Inválido"
            }
        }
        ////////////////////////senha
        binding.editTextSenha1Profissional.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextSenha1Profissional.addTextChangedListener {
                senhaValido1Profissional = senhaPattern.matches(binding.editTextSenha1Profissional.text.toString())
                if (senhaValido1Profissional) {
                    binding.layoutSenha1Profissional.error = null
                    senhaValido1Profissional = true
                } else
                    senhaValido1Profissional = false
                senhaValido2Profissional = binding.editTextSenha2Profissional.text.toString() == binding.editTextSenha1Profissional.text.toString()
                if (!senhaValido2Profissional)
                    senhaValido2Profissional = false
            }
            if (!hasFocus) {
                senhaValido1Profissional = senhaPattern.matches(binding.editTextSenha1Profissional.text.toString())
                if (!senhaValido1Profissional)
                    binding.layoutSenha1Profissional.error = "Senha Inválida"
            }
        }
        ////////////////congf senha
        binding.editTextSenha2Profissional.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextSenha2Profissional.addTextChangedListener {
                senhaValido2Profissional = binding.editTextSenha2Profissional.text.toString() == binding.editTextSenha1Profissional.text.toString()
                if (senhaValido2Profissional) {
                    binding.layoutSenha2Profissional.error = null
                    senhaValido2Profissional = true
                } else
                    senhaValido2Profissional = false
            }
            if (!hasFocus) {
                senhaValido2Profissional = binding.editTextSenha2Profissional.text.toString() == binding.editTextSenha1Profissional.text.toString()
                if (!senhaValido2Profissional)
                    binding.layoutSenha2Profissional.error = "Senhas Diferentes"
            }
        }

        binding.bttCancelarCadastroProfissional.setOnClickListener { finish() }

        binding.bttContinuarCadastroProfissional.setOnClickListener {
            clearFocus()
            if (nomeCompletoValidoProfissional &&
                dataNascimentoValidoProfissional &&
                enderecoValidoProfissional &&
                codigoPostalValidoProfissional &&
                cidadeValidoProfissional &&
                telemovelValidoProfissional &&
                telemovelValido2Profissional &&
                emailValidoProfissional &&
                senhaValido1Profissional &&
                senhaValido2Profissional
            ) {
                fazerCadastroProfissional()
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearFocus() {
        binding.editTextNomeCompletoProfissional.clearFocus()
        binding.editTextDataNascimentoProfissional.clearFocus()
        binding.editTextEnderecoProfissional.clearFocus()
        binding.editTextCodigoPostalProfissional.clearFocus()
        binding.editTextCidadeProfissional.clearFocus()
        binding.editTextTelemovel1Profissional.clearFocus()
        binding.editTextTelemovel2Profissional.clearFocus()
        binding.editTextEmailProfissional.clearFocus()
        binding.editTextSenha1Profissional.clearFocus()
        binding.editTextSenha2Profissional.clearFocus()
    }

    private fun fazerCadastroProfissional() {
        val email = binding.editTextEmailProfissional.text.toString().trim()
        val senha = binding.editTextSenha1Profissional.text.toString().trim()
        var nome = binding.editTextNomeCompletoProfissional.text.toString().split(" ")[0]
        if (nome.length > 10)
            nome.substring(0, 9) + "."

        mAuth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    user?.let {
                        val userId = user.uid
                        // Save user data to Realtime Database
                        val userData = HashMap<String, Any>()
                        userData["nomeCompleto"] = binding.editTextNomeCompletoProfissional.text.toString()
                        userData["nome"] = nome
                        userData["dataNascimento"] = binding.editTextDataNascimentoProfissional.text.toString()
                        userData["endereco"] = binding.editTextEnderecoProfissional.text.toString()
                        userData["codigoPostal"] = binding.editTextCodigoPostalProfissional.text.toString()
                        userData["cidade"] = binding.editTextCidadeProfissional.text.toString()
                        userData["telemovel1"] = binding.editTextTelemovel1Profissional.text.toString()
                        userData["telemovel2"] = binding.editTextTelemovel2Profissional.text.toString()
                        userData["email"] = binding.editTextEmailProfissional.text.toString()

                        mDatabase.child("Profissionais").child(userId).setValue(userData)
                            .addOnCompleteListener { task1 ->
                                if (task1.isSuccessful) {
                                    Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                                    // Navegar Para tela inicial do Profissional
                                    val navegarTelaPro = Intent(this, TelaProfissional::class.java)
                                    startActivity(navegarTelaPro)
                                } else {
                                    Toast.makeText(this, "Erro ao salvar dados do usuário.", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Falha no cadastro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}



