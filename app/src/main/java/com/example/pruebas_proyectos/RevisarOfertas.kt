package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebas_proyectos.databinding.ActivityRevisarOfertasBinding

class RevisarOfertas : AppCompatActivity() {

    private lateinit var binding: ActivityRevisarOfertasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRevisarOfertasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el rango de precios del Intent
        val rangoPrecios = intent.getStringExtra("RANGO_PRECIOS")

        // Configurar las ofertas basadas en el rango de precios
        when (rangoPrecios) {
            "$10,000 - $20,000" -> {
                mostrarOferta("PPC", rangoPrecios)
            }
            "$20,000 - $30,000" -> {
                mostrarOferta("KFC", rangoPrecios)
            }
            else -> {
                // Opción por defecto o mensaje de error si es necesario
                binding.textRangoPrecio.text = "No hay ofertas para este rango de precios."
            }
        }

        // Configuración del botón de regresar
        binding.btnRegresar.setOnClickListener {
            val intent = Intent(this, CaraPublico::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun mostrarOferta(nombreRestaurante: String, rangoPrecios: String) {
        binding.textOfertasrrevisadas.text = "Podrias gastar en $nombreRestaurante"
        binding.textRangoPrecio.text = "Rango de precios seleccionado: $rangoPrecios"
        // Aquí puedes personalizar aún más los elementos visuales para mostrar detalles adicionales
    }
}
