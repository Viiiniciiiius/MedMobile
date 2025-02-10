package com.example.medmobile.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.medmobile.databinding.ActivityInformacoesUteisBinding

class InformacoesUteis : AppCompatActivity() {

    private lateinit var binding: ActivityInformacoesUteisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformacoesUteisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bttVoltarIU.setOnClickListener { finish() }

    }
}