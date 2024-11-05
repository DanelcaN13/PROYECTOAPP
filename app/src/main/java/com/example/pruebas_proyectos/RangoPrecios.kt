package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pruebas_proyectos.databinding.ActivityRangoPreciosBinding

class RangoPrecios : AppCompatActivity() {

    private lateinit var binding: ActivityRangoPreciosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflar el layout y configurar el binding
        binding = ActivityRangoPreciosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar insets para la vista principal
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar el evento de clic para el botón de regresar
        binding.btnRegresar.setOnClickListener {
            val intent = Intent(this, CaraPublico::class.java)
            startActivity(intent)
            finish() // Cerrar esta actividad si no quieres volver a ella al presionar atrás
        }

        // Configurar el Spinner
        val opcionesRango = arrayOf("Seleccione un rango", "$10,000 - $20,000", "$30,000 - $40,000", "$50,000 - $60,000")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesRango)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRangoPrecios.adapter = adapter

        // Listener para el Spinner
        binding.spinnerRangoPrecios.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position) {
                    1 -> {
                        // Rango de $10,000 - $20,000
                        val intent = Intent(this@RangoPrecios, RevisarOfertas::class.java)
                        intent.putExtra("RANGO_PRECIOS", "$10,000 - $20,000") // Enviar el rango seleccionado
                        startActivity(intent)
                    }
                    2 -> {
                        // Rango de $30,000 - $40,000
                        val intent = Intent(this@RangoPrecios, RevisarOfertas::class.java)
                        intent.putExtra("RANGO_PRECIOS", "$30,000 - $40,000") // Enviar el rango seleccionado
                        startActivity(intent)
                    }
                    3 -> {
                        // Rango de $50,000 - $60,000
                        val intent = Intent(this@RangoPrecios, RevisarOfertas::class.java)
                        intent.putExtra("RANGO_PRECIOS", "$50,000 - $60,000") // Enviar el rango seleccionado
                        startActivity(intent)
                    }
                    else -> {
                        // No hacer nada si selecciona "Seleccione un rango"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada si no hay selección
            }
        }
    }
}

