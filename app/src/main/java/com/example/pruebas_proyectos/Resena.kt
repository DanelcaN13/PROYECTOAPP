package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pruebas_proyectos.databinding.ActivityResenaBinding

class Resena : AppCompatActivity() {

    private lateinit var binding: ActivityResenaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflar el layout
        binding = ActivityResenaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración para que el layout responda a los cambios en los insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar el evento de clic para el botón "Regresar"
        binding.btnRegresar.setOnClickListener {
            val intent = Intent(this, ResGuardado::class.java) // Cambia a ResGuardado
            startActivity(intent)
            finish() // Opcional: Cierra esta actividad
        }

        // Aquí puedes agregar la lógica adicional para mostrar reseñas o datos
    }
}
