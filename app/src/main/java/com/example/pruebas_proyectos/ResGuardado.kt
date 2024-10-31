package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pruebas_proyectos.databinding.ActivityResGuardadoBinding
import com.google.firebase.firestore.FirebaseFirestore

class ResGuardado : AppCompatActivity() {

    private lateinit var binding: ActivityResGuardadoBinding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflar el layout
        binding = ActivityResGuardadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar Firestore
        firestore = FirebaseFirestore.getInstance()

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

        // Obtener y mostrar los restaurantes
        mostrarRestaurante()
    }

    private fun mostrarRestaurante() {
        // Obtener el ID del restaurante desde el intent
        val idRestaurante = intent.getStringExtra("ID_RESTAURANTE")

        // Verificar que el ID no sea nulo
        if (idRestaurante != null) {
            // Recuperar el restaurante desde Firestore
            firestore.collection("restaurantes")
                .document(idRestaurante)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val nombre = document.getString("nombre") ?: "Nombre no disponible"
                        val direccion = document.getString("direccion") ?: "Dirección no disponible"
                        val calificacion = document.getLong("calificacion")?.toInt() ?: 0

                        binding.textKfc.text = nombre
                        binding.textDireccion.text = "Dirección: $direccion"
                        binding.textCalificacion.text = "Calificación: $calificacion"

                        // Mostrar estrellas según la calificación

                    } else {
                        Log.d("ResGuardado", "Restaurante no encontrado con ID: $idRestaurante")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("ResGuardado", "Error al recuperar restaurante: ${exception.message}")
                }
        } else {
            Log.e("ResGuardado", "ID de restaurante es nulo")
        }
    }
}




