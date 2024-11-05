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
            finish()
        }

        // Configurar el evento de clic para el logo
        binding.logo.setOnClickListener {
            val intent = Intent(this, Perfil::class.java)
            startActivity(intent)
        }

        // Configurar el evento de clic para "Danos tu opinión"
        binding.btnDanosTuOpinion.setOnClickListener {
            val intent = Intent(this, RestaurantesGuardados::class.java)
            startActivity(intent)
        }

        // Configurar el evento de clic para el botón "Agregar"
        binding.btnAgregar.setOnClickListener {
            val intent = Intent(this, AgregarRestaurante::class.java)
            startActivity(intent)
        }

        // Configurar el evento de clic para el botón "Descuentos"
        binding.btnDescuentos.setOnClickListener {
            val intent = Intent(this, Descuentos::class.java)
            startActivity(intent)
        }
    }
}

