package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pruebas_proyectos.databinding.ActivityDescuentosTerminadosBinding

class DescuentosTerminados : AppCompatActivity() {

    private lateinit var binding: ActivityDescuentosTerminadosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflar el layout y configurar el binding
        binding = ActivityDescuentosTerminadosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar insets para la vista principal
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar el evento de clic para el botón "Regresar"
        binding.btnRegresar.setOnClickListener {
            val intent = Intent(this, CaraPublico::class.java) // Redirigir a CaraPublico
            startActivity(intent)
            finish() // Opcional: cerrar la actividad actual
        }

        // Configurar la barra de navegación inferior
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Navegar a CaraPublico
                    startActivity(Intent(this, CaraPublico::class.java))
                    true
                }
                R.id.nav_favorites -> {
                    // Navegar a Favoritos Semanales
                    startActivity(Intent(this, FavoritosSemanales::class.java))
                    true
                }
                R.id.nav_profile -> {
                    // Navegar a Perfil
                    startActivity(Intent(this, Perfil::class.java))
                    true
                }
                else -> false
            }
        }
    }
}

