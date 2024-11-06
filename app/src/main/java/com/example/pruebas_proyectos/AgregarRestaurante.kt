package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AgregarRestaurante : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_restaurante)

        // Inicializar Firebase Firestore
        firestore = FirebaseFirestore.getInstance()

        // Configuración del botón regresar
        val btnRegresar = findViewById<ImageButton>(R.id.btn_regresar)
        btnRegresar.setOnClickListener {
            startActivity(Intent(this, CaraPublico::class.java))
            finish()
        }

        // Referencias a los campos de entrada
        val nombreRestauranteEditText = findViewById<EditText>(R.id.editTextNombreRestaurante)
        val direccionRestauranteEditText = findViewById<EditText>(R.id.editTextDireccion)
        val calificacionRestauranteEditText = findViewById<EditText>(R.id.editTextCalificacion)
        val resenaRestauranteEditText = findViewById<EditText>(R.id.editTextResena)
        val rangoPrecioEditText = findViewById<EditText>(R.id.editTextRangoPrecio)

        // Botón agregar restaurante para guardar el restaurante
        val btnAgregarRestaurante = findViewById<Button>(R.id.button2)
        btnAgregarRestaurante.setOnClickListener {
            val nombre = nombreRestauranteEditText.text.toString().trim()
            val direccion = direccionRestauranteEditText.text.toString().trim()
            val calificacionInput = calificacionRestauranteEditText.text.toString().trim()
            val resena = resenaRestauranteEditText.text.toString().trim()
            val rangoPrecio = rangoPrecioEditText.text.toString().trim()

            // Validación de campos
            if (nombre.isNotEmpty() && direccion.isNotEmpty() && calificacionInput.isNotEmpty() && resena.isNotEmpty() && rangoPrecio.isNotEmpty()) {
                val calificacion = calificacionInput.toIntOrNull()

                if (calificacion != null && calificacion in 1..5) {
                    agregarRestaurante(nombre, direccion, calificacion, resena, rangoPrecio)
                } else {
                    Toast.makeText(this, "La calificación debe ser un número entre 1 y 5", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para agregar restaurante a Firebase Firestore
    private fun agregarRestaurante(nombre: String, direccion: String, calificacion: Int, resena: String, rangoPrecio: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        // Crear un nuevo restaurante
        val restaurante = hashMapOf(
            "nombre" to nombre,
            "direccion" to direccion,
            "calificacion" to calificacion,
            "resena" to resena,
            "rangoPrecio" to rangoPrecio
        )

        // Agregar el restaurante a la colección "restaurantes" en Firestore
        firestore.collection("restaurantes").add(restaurante)
            .addOnSuccessListener {
                // Guardar también el restaurante en la subcolección de "restaurantes_semanales"
                firestore.collection("usuarios")
                    .document(userId)
                    .collection("restaurantes_semanales")
                    .add(restaurante)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Restaurante agregado y guardado como favorito de la semana", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al guardar en favoritos semanales", Toast.LENGTH_SHORT).show()
                    }

                Toast.makeText(this, "Restaurante agregado a la base de datos", Toast.LENGTH_SHORT).show()
                finish() // Cerrar la actividad
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al agregar restaurante: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

