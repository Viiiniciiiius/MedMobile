package com.example.medmobile.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.medmobile.databinding.ActivityConfiguracoesProfissionalBinding

class ConfiguracoesProfissional : AppCompatActivity() {
    private lateinit var binding: ActivityConfiguracoesProfissionalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracoesProfissionalBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.bttVoltarConfiguraceos.setOnClickListener { finish() }
        binding.bttSalvarConfiguracoes.setOnClickListener { trasicaoSalvarConfiguraceos() }

    }

    private fun trasicaoSalvarConfiguraceos(){
        finish()
    }
}