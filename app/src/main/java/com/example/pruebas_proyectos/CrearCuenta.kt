package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CrearCuenta : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_cuenta)

        // Ajustes para el padding en el layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuración de los campos de entrada
        val nombre = findViewById<EditText>(R.id.input_nombre)
        val apellido = findViewById<EditText>(R.id.input_apellido)
        val correo = findViewById<EditText>(R.id.input_correo)
        val usuario = findViewById<EditText>(R.id.input_usuario)
        val contrasena = findViewById<EditText>(R.id.input_contrasena)
        val btnEntrar = findViewById<TextView>(R.id.entrar)

        // Configurar el evento de clic para el botón "Regresar" (flecha)
        val btnRegresar = findViewById<ImageButton>(R.id.btn_regresar)
        btnRegresar.setOnClickListener {
            // Intent para volver a la actividad Inicio
            val intent = Intent(this, Inicio::class.java)
            startActivity(intent)
            finish() // Cerrar esta actividad para que no esté en el stack
        }

        // Validación de los campos cuando el botón "Entrar" es presionado
        btnEntrar.setOnClickListener {
            try {
                val esNombreValido = validarNombreApellido(nombre.text.toString())
                val esApellidoValido = validarNombreApellido(apellido.text.toString())
                val esCorreoValido = validarCorreo(correo.text.toString())
                val esUsuarioValido = validarUsuario(usuario.text.toString())
                val esContrasenaValida = validarContrasena(contrasena.text.toString())

                if (esNombreValido && esApellidoValido && esCorreoValido && esUsuarioValido && esContrasenaValida) {
                    // Si todas las validaciones son exitosas, procede con el registro
                    val textoNombre = nombre.text.toString().trim()
                    val textoApellido = apellido.text.toString().trim()
                    val textoCorreo = correo.text.toString().trim()
                    val textoContrasena = contrasena.text.toString().trim()
                    val textoUsuario = usuario.text.toString().trim()

                    // Llamada a la base de datos
                    Crearbd.agregarRegistro(
                        textoNombre,
                        textoApellido,
                        textoCorreo,
                        textoContrasena,
                        textoUsuario
                    )

                    // Redirigir a la actividad Inicio
                    val intent = Intent(this, Inicio::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    mostrarError("Por favor, corrige los errores en el formulario antes de continuar.")
                }
            } catch (e: Exception) {
                mostrarError("Ocurrió un error: ${e.message}")
            }
        }
    }

    // Función para validar Nombre y Apellido (solo letras y espacios)
    private fun validarNombreApellido(texto: String): Boolean {
        val regex = Regex("^[a-zA-Z ]*\$") // Permite letras y espacios, incluyendo vacío
        return if (!regex.matches(texto)) {
            mostrarError("Nombre y apellido deben contener solo letras y espacios.")
            false
        } else true
    }

    // Función para validar el Correo electrónico (debe contener '@')
    private fun validarCorreo(correo: String): Boolean {
        val regex = Regex("^[\\w.-]+@[\\w.-]+\\.\\w+\$")
        return if (!regex.matches(correo)) {
            mostrarError("Ingrese un correo electrónico válido.")
            false
        } else true
    }

    // Función para validar el Nombre de usuario (al menos una letra mayúscula y un número)
    private fun validarUsuario(usuario: String): Boolean {
        val regex = Regex("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]+\$")
        return if (!regex.matches(usuario)) {
            mostrarError("El nombre de usuario debe contener al menos una letra mayúscula y un número.")
            false
        } else true
    }

    // Función para validar la Contraseña (al menos una letra mayúscula, un número y un carácter especial)
    private fun validarContrasena(contrasena: String): Boolean {
        val regex = Regex("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^+=!?&*().]).{8,}\$")  // Caracteres especiales añadidos
        return if (!regex.matches(contrasena)) {
            mostrarError("La contraseña debe tener al menos una letra mayúscula, un número y un carácter especial.")
            false
        } else true
    }

    // Función para mostrar errores como mensajes emergentes (Toast)
    private fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
