package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
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

        Log.d("Inicio", "onCreate: activity started")

        // Inicializar los botones y campos
        val btnRegresar = findViewById<TextView>(R.id.regresar)
        val btnIngresar = findViewById<TextView>(R.id.ingresar)
        val editTextUsuario = findViewById<EditText>(R.id.input_field)
        val editTextContrasena = findViewById<EditText>(R.id.input_field_password)

        // Verificar si los botones y campos están correctamente inicializados
        if (btnRegresar == null || btnIngresar == null || editTextUsuario == null || editTextContrasena == null) {
            Toast.makeText(this, "Error al inicializar vistas", Toast.LENGTH_SHORT).show()
            return
        }

        // Configurar el evento de clic para el botón "Regresar"
        btnRegresar.setOnClickListener {
            val intent = Intent(this, PaginaPrincipal::class.java)
            startActivity(intent)
            finish()
        }

        // Configurar el evento de clic para el botón "Ingresar"
        btnIngresar.setOnClickListener {
            // Obtener el nombre de usuario y la contraseña ingresados
            val usuario = editTextUsuario.text.toString().trim()
            val contrasena = editTextContrasena.text.toString().trim()

            // Validar el nombre de usuario y la contraseña
            if (usuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("Inicio", "Intentando iniciar sesión con usuario: $usuario")
                // Usa el callback de iniciarSesion
                Crearbd.iniciarSesion(usuario, contrasena) { success ->
                    if (success) {
                        // Inicio de sesión exitoso
                        Log.d("Inicio", "Inicio de sesión exitoso para el usuario: $usuario")
                        val intent = Intent(this, CaraPublico::class.java)
                        startActivity(intent)
                        finish()
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
