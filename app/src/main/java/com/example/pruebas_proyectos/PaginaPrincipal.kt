package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PaginaPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_principal)

        // Ajustes para el padding en el layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuración del TextView para iniciar sesión
        val iniciarSesion: TextView = findViewById(R.id.ingresar)
        iniciarSesion.setOnClickListener {
            // Cambia Inicio por el nombre correcto de tu actividad
            val intent = Intent(this, Inicio::class.java)
            startActivity(intent)
        }

        // Configuración del TextView para registrarse
        val registrarse: TextView = findViewById(R.id.regresar)
        registrarse.setOnClickListener {
            // Cambia PaginaPrincipal por CrearCuenta
            val intent = Intent(this, CrearCuenta::class.java)
            startActivity(intent)
        }
    }
}

