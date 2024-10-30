package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebas_proyectos.databinding.ActivityCaraPublicoBinding

class CaraPublico : AppCompatActivity() {

    private lateinit var binding: ActivityCaraPublicoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCaraPublicoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el evento de clic para el botón "Regresar"
        binding.btnRegresar.setOnClickListener {
            // Intent para volver a la actividad Inicio
            val intent = Intent(this, Inicio::class.java)
            startActivity(intent)
            finish() // Cerrar esta actividad para que no esté en el stack
        }

        // Configurar el evento de clic para el logo
        binding.logo.setOnClickListener {
            // Intent para ir a la actividad Perfil
            val intent = Intent(this, Perfil::class.java)
            startActivity(intent)
        }
    }
}