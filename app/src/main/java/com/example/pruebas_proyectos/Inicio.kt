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
        val editTextUsuario = findViewById<EditText>(R.id.input_field) // Asegúrate de tener un EditText para el nombre de usuario
        val editTextContrasena = findViewById<EditText>(R.id.input_field_password) // Asegúrate de tener un EditText para la contraseña

        btnIngresar.setOnClickListener {
            // Obtener el nombre de usuario y la contraseña ingresados
            val usuario = editTextUsuario.text.toString().trim()
            val contrasena = editTextContrasena.text.toString().trim()

            // Validar el nombre de usuario y la contraseña
            if (usuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Usa el callback de iniciarSesion
                Crearbd.iniciarSesion(usuario, contrasena) { success ->
                    if (success) {
                        // Inicio de sesión exitoso
                        val intent = Intent(this, CaraPublico::class.java)
                        startActivity(intent)
                    } else {
                        // Credenciales incorrectas
                        Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    // Función para mostrar errores como mensajes emergentes (Toast)
    private fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
