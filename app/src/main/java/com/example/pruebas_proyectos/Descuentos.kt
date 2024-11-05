package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pruebas_proyectos.databinding.ActivityDescuentosBinding

class Descuentos : AppCompatActivity() {

    private lateinit var binding: ActivityDescuentosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflar el layout y configurar el binding
        binding = ActivityDescuentosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar insets para la vista principal
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar el evento de clic para el botón "Regresar"
        binding.btnRegresar.setOnClickListener {
            // Redirigir a la actividad anterior o a una actividad deseada
            finish() // Si deseas que cierre esta actividad y regrese a la anterior
        }

        // Configurar el evento de clic para el botón "Revisar Ofertas"
        binding.btnRevisarOpinion.setOnClickListener {
            val intent = Intent(this, DescuentosTerminados::class.java) // Cambiar aquí
            startActivity(intent) // Cambiar a DescuentosTerminados
        }
    }
}



