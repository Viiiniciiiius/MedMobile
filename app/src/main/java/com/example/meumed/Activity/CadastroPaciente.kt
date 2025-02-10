
package com.example.medmobile.Activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.medmobile.databinding.ActivityCadastroPacienteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.HashMap

class CadastroPaciente : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroPacienteBinding
    private var nomeCompletoValidoPaciente = false
    private var dataNascimentoValidoPaciente = false
    private var enderecoValidoPaciente = false
    private var codigoPostalValidoPaciente = false
    private var cidadeValidoPaciente = false
    private var emailValidoPaciente = true
    private var telemovelValidoPaciente = false
    private var telemovelValido2Paciente = true
    private var senhaValido1Paciente = false
    private var senhaValido2Paciente = false

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroPacienteBinding.inflate(layoutInflater)
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


        binding.editTextNomeCompletoPaciente.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextNomeCompletoPaciente.addTextChangedListener {
                nomeCompletoValidoPaciente = nomeCompletoPattern.matches(binding.editTextNomeCompletoPaciente.text.toString())
                if (nomeCompletoValidoPaciente) {
                    binding.layoutNomeCompletoPaciente.error = null
                    nomeCompletoValidoPaciente = true
                } else
                    nomeCompletoValidoPaciente = false
            }
            if (!hasFocus) {
                nomeCompletoValidoPaciente = nomeCompletoPattern.matches(binding.editTextNomeCompletoPaciente.text.toString())
                if (!nomeCompletoValidoPaciente)
                    binding.layoutNomeCompletoPaciente.error = "Nome Inválido"
            }
        }
/////////////////////////data de nascimento

        binding.editTextDataNascimentoPaciente.addTextChangedListener(object : TextWatcher {
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
                binding.editTextDataNascimentoPaciente.setText(formatted.toString())
                binding.editTextDataNascimentoPaciente.setSelection(formatted.length)
                isUpdating = false

                dataNascimentoValidoPaciente = dataNascimentoPattern.matches(binding.editTextDataNascimentoPaciente.text.toString())
                if (dataNascimentoValidoPaciente) {
                    binding.layoutDataNacimentoPaciente.error = null
                    dataNascimentoValidoPaciente = true
                } else
                    dataNascimentoValidoPaciente = false
            }
        })
        binding.editTextDataNascimentoPaciente.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                dataNascimentoValidoPaciente = dataNascimentoPattern.matches(binding.editTextDataNascimentoPaciente.text.toString())
                if (!dataNascimentoValidoPaciente)
                    binding.layoutDataNacimentoPaciente.error = "Nome Inválido"
            }
        }
//ENDERECO
        binding.editTextEnderecoPaciente.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextEnderecoPaciente.addTextChangedListener {
                enderecoValidoPaciente = enderecoPattern.matches(binding.editTextEnderecoPaciente.text.toString())
                if (enderecoValidoPaciente) {
                    binding.layoutEnderecoPaciente.error = null
                    enderecoValidoPaciente = true
                } else
                    enderecoValidoPaciente = false
            }
            if (!hasFocus) {
                enderecoValidoPaciente = enderecoPattern.matches(binding.editTextEnderecoPaciente.text.toString())
                if (!enderecoValidoPaciente)
                    binding.layoutEnderecoPaciente.error = "Endereço Inválido"
            }
        }
        /////////codigo postal
        binding.editTextCodigoPostalPaciente.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextCodigoPostalPaciente.addTextChangedListener {
                codigoPostalValidoPaciente = codigoPostalPattern.matches(binding.editTextCodigoPostalPaciente.text.toString())
                if (codigoPostalValidoPaciente) {
                    binding.layoutCodigoPostalPaciente.error = null
                    codigoPostalValidoPaciente = true
                } else
                    codigoPostalValidoPaciente = false
            }
            if (!hasFocus) {
                codigoPostalValidoPaciente = codigoPostalPattern.matches(binding.editTextCodigoPostalPaciente.text.toString())
                if (!codigoPostalValidoPaciente)
                    binding.layoutCodigoPostalPaciente.error = "Código Postal Inválido"
            }
        }


        ////////////////cidade
        binding.editTextCidadePaciente.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextCidadePaciente.addTextChangedListener {
                cidadeValidoPaciente = cidadePattern.matches(binding.editTextCidadePaciente.text.toString())
                if (cidadeValidoPaciente) {
                    binding.layoutCidadePaciente.error = null
                    cidadeValidoPaciente = true
                } else
                    cidadeValidoPaciente = false
            }
            if (!hasFocus) {
                cidadeValidoPaciente = cidadePattern.matches(binding.editTextCidadePaciente.text.toString())
                if (!cidadeValidoPaciente)
                    binding.layoutCidadePaciente.error = "Cidade Inválida"
            }
        }

        /////////telemovel
        binding.editTextTelemovel1Paciente.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextTelemovel1Paciente.addTextChangedListener {
                telemovelValidoPaciente = telemovelPattern.matches(binding.editTextTelemovel1Paciente.text.toString())
                if (telemovelValidoPaciente || binding.editTextTelemovel1Paciente.text.toString().isEmpty()) {
                    binding.layoutTelemovel1Paciente.error = null
                    telemovelValidoPaciente = true
                } else
                    telemovelValidoPaciente = false
            }
            if (!hasFocus) {
                telemovelValidoPaciente =
                    telemovelPattern.matches(binding.editTextTelemovel1Paciente.text.toString())
                if (!telemovelValidoPaciente && binding.editTextTelemovel1Paciente.text.toString().isNotEmpty()){
                    binding.layoutTelemovel1Paciente.error = "Telemóvel Inválido"
                }else
                    telemovelValidoPaciente = true
            }
        }
/////////////////////////tele2
        binding.editTextTelemovel2Paciente.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextTelemovel2Paciente.addTextChangedListener {
                telemovelValido2Paciente = telemovelPattern.matches(binding.editTextTelemovel2Paciente.text.toString())
                if (telemovelValido2Paciente || binding.editTextTelemovel2Paciente.text.toString().isEmpty()) {
                    binding.layoutTelemovel2Paciente.error = null
                    telemovelValido2Paciente = true
                } else
                    telemovelValido2Paciente = false
            }
            if (!hasFocus) {
                telemovelValido2Paciente =
                    telemovelPattern.matches(binding.editTextTelemovel2Paciente.text.toString())
                if (!telemovelValido2Paciente && binding.editTextTelemovel2Paciente.text.toString().isNotEmpty()){
                    binding.layoutTelemovel2Paciente.error = "Telemóvel Inválido"
                }else
                    telemovelValido2Paciente = true
            }
        }
////////////////////////email
        binding.editTextEmailPaciente.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextEmailPaciente.addTextChangedListener {
                emailValidoPaciente = emailPattern.matches(binding.editTextEmailPaciente.text.toString())
                if (emailValidoPaciente) {
                    binding.layoutEmailPaciente.error = null
                    emailValidoPaciente = true
                } else
                    emailValidoPaciente = false
            }
            if (!hasFocus) {
                emailValidoPaciente = emailPattern.matches(binding.editTextEmailPaciente.text.toString())
                if (!emailValidoPaciente)
                    binding.layoutEmailPaciente.error = "Email Inválido"
            }
        }
        ////////////////////////senha
        binding.editTextSenha1Paciente.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextSenha1Paciente.addTextChangedListener {
                senhaValido1Paciente = senhaPattern.matches(binding.editTextSenha1Paciente.text.toString())
                if (senhaValido1Paciente) {
                    binding.layoutSenha1Paciente.error = null
                    senhaValido1Paciente = true
                } else
                    senhaValido1Paciente = false
                senhaValido2Paciente = binding.editTextSenha2Paciente.text.toString() == binding.editTextSenha1Paciente.text.toString()
                if (!senhaValido2Paciente)
                    senhaValido2Paciente = false
            }
            if (!hasFocus) {
                senhaValido1Paciente = senhaPattern.matches(binding.editTextSenha1Paciente.text.toString())
                if (!senhaValido1Paciente)
                    binding.layoutSenha1Paciente.error = "Senha Inválida"
            }
        }
        ////////////////congf senha
        binding.editTextSenha2Paciente.setOnFocusChangeListener { _, hasFocus ->
            binding.editTextSenha2Paciente.addTextChangedListener {
                senhaValido2Paciente = binding.editTextSenha2Paciente.text.toString() == binding.editTextSenha1Paciente.text.toString()
                if (senhaValido2Paciente) {
                    binding.layoutSenha2Paciente.error = null
                    senhaValido2Paciente = true
                } else
                    senhaValido2Paciente = false
            }
            if (!hasFocus) {
                senhaValido2Paciente = binding.editTextSenha2Paciente.text.toString() == binding.editTextSenha1Paciente.text.toString()
                if (!senhaValido2Paciente)
                    binding.layoutSenha2Paciente.error = "Senhas Diferentes"
            }
        }

        binding.bttCancelarCadastroPaciente.setOnClickListener { finish() }

        binding.bttContinuarCadastroPaciente.setOnClickListener {
            clearFocus()
            if (nomeCompletoValidoPaciente &&
                dataNascimentoValidoPaciente &&
                enderecoValidoPaciente &&
                codigoPostalValidoPaciente &&
                cidadeValidoPaciente &&
                telemovelValidoPaciente &&
                telemovelValido2Paciente &&
                emailValidoPaciente &&
                senhaValido1Paciente &&
                senhaValido2Paciente
            ) {
                fazerCadastroPaciente()
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearFocus() {
        binding.editTextNomeCompletoPaciente.clearFocus()
        binding.editTextDataNascimentoPaciente.clearFocus()
        binding.editTextEnderecoPaciente.clearFocus()
        binding.editTextCodigoPostalPaciente.clearFocus()
        binding.editTextCidadePaciente.clearFocus()
        binding.editTextTelemovel1Paciente.clearFocus()
        binding.editTextTelemovel2Paciente.clearFocus()
        binding.editTextEmailPaciente.clearFocus()
        binding.editTextSenha1Paciente.clearFocus()
        binding.editTextSenha2Paciente.clearFocus()
    }

    private fun fazerCadastroPaciente() {
        val email = binding.editTextEmailPaciente.text.toString().trim()
        val senha = binding.editTextSenha1Paciente.text.toString().trim()
        var nome = binding.editTextNomeCompletoPaciente.text.toString().split(" ")[0]
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
                        userData["nomeCompleto"] = binding.editTextNomeCompletoPaciente.text.toString()
                        userData["nome"] = nome
                        userData["dataNascimento"] = binding.editTextDataNascimentoPaciente.text.toString()
                        userData["endereco"] = binding.editTextEnderecoPaciente.text.toString()
                        userData["codigoPostal"] = binding.editTextCodigoPostalPaciente.text.toString()
                        userData["cidade"] = binding.editTextCidadePaciente.text.toString()
                        userData["telemovel1"] = binding.editTextTelemovel1Paciente.text.toString()
                        userData["telemovel2"] = binding.editTextTelemovel2Paciente.text.toString()
                        userData["email"] = binding.editTextEmailPaciente.text.toString()

                        mDatabase.child("Pacientes").child(userId).setValue(userData)
                            .addOnCompleteListener { task1 ->
                                if (task1.isSuccessful) {
                                    Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                                    // Navegar Para tela inicial do paciente
                                    val navegarTelaPac = Intent(this, TelaPaciente::class.java)
                                    startActivity(navegarTelaPac)
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



