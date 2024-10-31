package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class Perfil : AppCompatActivity() {
    private lateinit var editTextUsuario: EditText
    private lateinit var editTextNombre: EditText
    private lateinit var editTextApellido: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextContrasena: EditText
    private lateinit var btnGuardar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)

        // Configurar el comportamiento del botón de regresar
        val btnRegresar: ImageButton = findViewById(R.id.btn_regresar)
        btnRegresar.setOnClickListener {
            // Iniciar la actividad Crear Cuenta
            val intent = Intent(this, CrearCuenta::class.java)
            startActivity(intent)
            finish()
        }

        // Inicializar EditTexts y el botón "Guardar"
        editTextUsuario = findViewById(R.id.input_usuario)
        editTextNombre = findViewById(R.id.input_nombre)
        editTextApellido = findViewById(R.id.input_apellido)
        editTextCorreo = findViewById(R.id.input_correo)
        editTextContrasena = findViewById(R.id.input_contrasena)
        btnGuardar = findViewById(R.id.guardar)

        // Configurar el evento para guardar cambios en el perfil
        btnGuardar.setOnClickListener {
            // Obtener los datos ingresados
            val usuario = editTextUsuario.text.toString()
            val nombre = editTextNombre.text.toString()
            val apellido = editTextApellido.text.toString()
            val correo = editTextCorreo.text.toString()
            val contrasena = editTextContrasena.text.toString()

            // Validar los campos
            if (validarUsuario(usuario) && validarNombreApellido(nombre) && validarNombreApellido(apellido)
                && validarCorreo(correo) && validarContrasena(contrasena)) {
                // Aquí puedes manejar el caso de guardar los cambios en el perfil
                mostrarError("Datos guardados correctamente.")

                // Iniciar la actividad de Inicio
                val intent = Intent(this, Inicio::class.java) // Asegúrate de que 'Inicio' sea el nombre de tu actividad
                startActivity(intent)
                finish() // Opcional, para cerrar la actividad actual
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Función para validar el Nombre de usuario (al menos una letra mayúscula y un número)
    private fun validarUsuario(usuario: String): Boolean {
        val regex = Regex("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]+$")
        return if (!regex.matches(usuario)) {
            mostrarError("El nombre de usuario debe contener al menos una letra mayúscula y un número.")
            false
        } else true
    }

    // Función para validar Nombre y Apellido (solo letras y espacios)
    private fun validarNombreApellido(nombre: String): Boolean {
        val regex = Regex("^[a-zA-Z\\s]+$") // Solo letras y espacios
        return if (!regex.matches(nombre)) {
            mostrarError("El nombre y el apellido deben contener solo letras y espacios.")
            false
        } else true
    }

    // Función para validar el Correo electrónico (debe contener un @)
    private fun validarCorreo(correo: String): Boolean {
        return if (!correo.contains("@")) {
            mostrarError("Por favor, ingrese un correo electrónico válido que contenga '@'.")
            false
        } else true
    }

    // Función para validar la Contraseña (al menos una letra mayúscula, un número y un carácter especial)
    private fun validarContrasena(contrasena: String): Boolean {
        val regex = Regex("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!.]).{8,}$")
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
