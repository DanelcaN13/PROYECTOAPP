package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        // Obtener los datos del restaurante y mostrarlos
        mostrarRestaurante()
    }

    private fun mostrarRestaurante() {
        // Obtener el ID del restaurante desde el intent
        val idRestaurante = intent.getStringExtra("ID_RESTAURANTE")

        // Verificar que el ID no sea nulo
        if (idRestaurante != null) {
            RestauranteManager.obtenerRestaurantePorId(idRestaurante) { restaurante ->
                // Verificar que el restaurante no sea nulo
                if (restaurante != null) {
                    binding.textKfc.text = restaurante.nombre // Asegúrate de que el nombre de la propiedad sea correcto
                    binding.textDireccion.text = "Dirección: ${restaurante.direccion}"
                    binding.textCalificacion.text = "Calificación: ${restaurante.calificacion}"

                    // Aquí puedes agregar lógica para mostrar estrellas según la calificación
                    val calificacion = restaurante.calificacion.toIntOrNull() ?: 0
                    mostrarEstrellas(calificacion)
                } else {
                    Log.d("ResGuardado", "Restaurante no encontrado con ID: $idRestaurante")
                }
            }
        } else {
            Log.e("ResGuardado", "ID de restaurante es nulo")
        }
    }

    private fun mostrarEstrellas(calificacion: Int) {
        val estrellas = listOf(
            binding.star1,
            binding.star2,
            binding.star3,
            binding.star4,
            binding.star5
        )

        for (i in estrellas.indices) {
            if (i < calificacion) {
                estrellas[i].setImageResource(R.drawable.estrella) // Cambia a la estrella llena
            } else {
                estrellas[i].setImageResource(R.drawable.estrella_no) // Cambia a la estrella vacía
            }
        }
    }
}
