package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AgregarRestaurante : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_restaurante)

        // Inicializar Firebase Database
        database = FirebaseDatabase.getInstance().getReference("restaurantes")

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

        // Botón enviar para guardar el restaurante
        val btnEnviar = findViewById<Button>(R.id.btn_enviar)
        btnEnviar.setOnClickListener {
            val nombre = nombreRestauranteEditText.text.toString().trim()
            val direccion = direccionRestauranteEditText.text.toString().trim()
            val calificacionInput = calificacionRestauranteEditText.text.toString().trim()
            val resena = resenaRestauranteEditText.text.toString().trim()

            // Validación de campos
            if (nombre.isNotEmpty() && direccion.isNotEmpty() && calificacionInput.isNotEmpty() && resena.isNotEmpty()) {
                val calificacion = calificacionInput.toIntOrNull()

                if (calificacion != null && calificacion in 1..5) {
                    agregarRestaurante(nombre, direccion, calificacion, resena)
                } else {
                    Toast.makeText(this, "La calificación debe ser un número entre 1 y 5", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para agregar restaurante a Firebase
    private fun agregarRestaurante(nombre: String, direccion: String, calificacion: Int, resena: String) {
        // Crear un nuevo restaurante usando .push() para generar un ID único
        val restaurante = hashMapOf(
            "nombre" to nombre,
            "direccion" to direccion,
            "calificacion" to calificacion,
            "resena" to resena // Agrega la reseña
        )

        // Agregar el restaurante a la base de datos
        database.push().setValue(restaurante)
            .addOnSuccessListener {
                Toast.makeText(this, "Restaurante agregado", Toast.LENGTH_SHORT).show()
                finish() // Cerrar la actividad
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al agregar restaurante: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
