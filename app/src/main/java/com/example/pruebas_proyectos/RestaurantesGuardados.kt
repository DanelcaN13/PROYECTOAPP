package com.example.pruebas_proyectos

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.example.pruebas_proyectos.databinding.ActivityRestaurantesGuardadosBinding

class RestaurantesGuardados : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantesGuardadosBinding
    private lateinit var database: DatabaseReference
    private lateinit var restauranteAdapter: RestauranteAdapter
    private val listaRestaurantes = mutableListOf<Restaurante>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRestaurantesGuardadosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajuste de padding para los insets del sistema
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar Firebase Database
        database = FirebaseDatabase.getInstance().getReference("restaurantes")

        // Configurar RecyclerView
        restauranteAdapter = RestauranteAdapter(listaRestaurantes)
        binding.recyclerViewRestaurantes.adapter = restauranteAdapter
        binding.recyclerViewRestaurantes.layoutManager = LinearLayoutManager(this)

        // Obtener los restaurantes desde Firebase
        obtenerRestaurantes()

        // Configurar el botón de regresar
        binding.btnRegresar.setOnClickListener {
            finish() // Finaliza esta actividad
        }
    }

    private fun obtenerRestaurantes() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaRestaurantes.clear() // Limpiar la lista antes de agregar nuevos datos
                for (restauranteSnapshot in snapshot.children) {
                    val restaurante = restauranteSnapshot.getValue(Restaurante::class.java)
                    if (restaurante != null) {
                        listaRestaurantes.add(restaurante) // Agregar el restaurante a la lista
                        Log.d("FirebaseData", "Restaurante: ${restaurante.nombre}, Rango Precio: ${restaurante.rangoPrecio}") // Depuración
                    } else {
                        Log.e("FirebaseData", "Error: Restaurante es nulo")
                    }
                }
                restauranteAdapter.notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar errores aquí
                Log.e("FirebaseData", "Error al obtener datos: ${error.message}")
            }
        })
    }
}



