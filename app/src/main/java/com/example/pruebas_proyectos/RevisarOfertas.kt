package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pruebas_proyectos.databinding.ActivityRevisarOfertasBinding

class RevisarOfertas : AppCompatActivity() {

    private lateinit var binding: ActivityRevisarOfertasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflar el layout y configurar el binding
        binding = ActivityRevisarOfertasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar insets para la vista principal
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar el evento de clic para el botón "Regresar"
        binding.btnRegresar.setOnClickListener {
            val intent = Intent(this, Descuentos::class.java)
            startActivity(intent)
        }
    }
}
