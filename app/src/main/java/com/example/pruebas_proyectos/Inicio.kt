package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Crear una instancia de MyDatabaseHelper
        val dbHelper = MyDatabaseHelper(this)

        // Configurar el evento de clic para el botón "Regresar"
        val btnRegresar = findViewById<TextView>(R.id.regresar)
        btnRegresar.setOnClickListener {
            // Intent para volver a la actividad PaginaPrincipal
            val intent = Intent(this, PaginaPrincipal::class.java)
            startActivity(intent)
            finish() // Cerrar esta actividad para que no esté en el stack
        }

        // Configurar el evento de clic para el botón "Ingresar"
        val btnIngresar = findViewById<TextView>(R.id.ingresar) // Usa TextView o Button según tu elección
        val editTextUsuario = findViewById<EditText>(R.id.input_field) // Asegúrate de tener un EditText en tu layout para el nombre de usuario

        btnIngresar.setOnClickListener {
            // Obtener el nombre de usuario ingresado
            val usuario = editTextUsuario.text.toString()

            // Verificar si el usuario existe
            if (dbHelper.verificarUsuario(usuario)) {
                // El usuario existe
                // Aquí puedes manejar el caso (ej., ir a CaraPublico)
                val intent = Intent(this, CaraPublico::class.java)
                startActivity(intent)
            } else {
                // El usuario no existe
                // Aquí puedes manejar el caso (ej., mostrar un mensaje de error)
                Toast.makeText(this, "El usuario no existe. Por favor verifica tus datos.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
