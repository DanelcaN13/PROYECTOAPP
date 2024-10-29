package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageButton

class Perfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)

        // Configurar el comportamiento del botón de regresar
        val btnRegresar: ImageButton = findViewById(R.id.btn_regresar)
        btnRegresar.setOnClickListener {
            // Iniciar la actividad Crear Cuenta
            val intent = Intent(this, CrearCuenta::class.java) // Cambia al nombre de tu actividad de crear cuenta
            startActivity(intent)
            finish() // Opcional, para cerrar la actividad actual
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
