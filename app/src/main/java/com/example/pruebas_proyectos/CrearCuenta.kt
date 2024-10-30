package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CrearCuenta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_cuenta)

        // Ajustes para el padding en el layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuración del botón de regresar
        val btnRegresar = findViewById<ImageButton>(R.id.btn_regresar)
        btnRegresar.setOnClickListener {
            // Volver a PaginaPrincipal
            val intent = Intent(this, PaginaPrincipal::class.java)
            startActivity(intent)
            finish() // Opcional: cerrar esta actividad para que no esté en el stack
        }

        // Configuración del botón Entrar para redirigir a Inicio
        val btnEntrar = findViewById<TextView>(R.id.entrar)
        btnEntrar.setOnClickListener {
            // Abrir Inicio
            val intent = Intent(this, Inicio::class.java)
            startActivity(intent)
            finish() // Opcional: cerrar esta actividad para que no esté en el stack
        }
    }
}

