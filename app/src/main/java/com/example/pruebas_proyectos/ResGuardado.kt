package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pruebas_proyectos.databinding.ActivityResGuardadoBinding

class ResGuardado : AppCompatActivity() {

    private lateinit var binding: ActivityResGuardadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflar el layout
        binding = ActivityResGuardadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración para que el layout responda a los cambios en los insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar el evento de clic para el botón "Regresar"
        binding.btnRegresar.setOnClickListener {
            val intent = Intent(this, CaraPublico::class.java)
            startActivity(intent)
            finish()
        }

        // Configurar el evento de clic para el botón "Calificar"
        binding.ellipse5.setOnClickListener {
            val intent = Intent(this, Resena::class.java) // Cambia a Resena
            startActivity(intent)
        }
    }
}
