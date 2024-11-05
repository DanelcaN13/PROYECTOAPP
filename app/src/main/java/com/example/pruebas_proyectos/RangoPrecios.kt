package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebas_proyectos.databinding.ActivityRangoPreciosBinding

class RangoPrecios : AppCompatActivity() {

    private lateinit var binding: ActivityRangoPreciosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRangoPreciosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el Spinner
        val opcionesRango = arrayOf("Seleccione un rango", "$10,000 - $20,000", "$20,000 - $30,000")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesRango)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRangoPrecios.adapter = adapter

        // Listener para el Spinner
        binding.spinnerRangoPrecios.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val rangoSeleccionado = when (position) {
                    1 -> "$10,000 - $20,000"
                    2 -> "$20,000 - $30,000"
                    else -> null
                }

                rangoSeleccionado?.let {
                    val intent = Intent(this@RangoPrecios, RevisarOfertas::class.java)
                    intent.putExtra("RANGO_PRECIOS", it)
                    startActivity(intent)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Configurar el evento de clic para el botón "Regresar"
        binding.btnRegresar.setOnClickListener {
            val intent = Intent(this, CaraPublico::class.java) // Redirigir a CaraPublico
            startActivity(intent)
            finish() // Opcional: cerrar la actividad actual
        }
    }
}
