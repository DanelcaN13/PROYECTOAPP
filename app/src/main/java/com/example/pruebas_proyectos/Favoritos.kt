package com.example.pruebas_proyectos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Favoritos : AppCompatActivity() {
    private lateinit var favoritosAdapter: RestaurantesFavoritosAdapter
    private val restaurantesFavoritos = mutableListOf<RestauranteManager.Restaurante>()
    private val usuarioId = "ID_DEL_USUARIO" // Reemplaza con el ID del usuario autenticado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favoritos)

        // Configuración de padding para evitar la superposición con barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFavoritos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        favoritosAdapter = RestaurantesFavoritosAdapter(restaurantesFavoritos) { restauranteId ->
            eliminarDeFavoritos(restauranteId)
        }
        recyclerView.adapter = favoritosAdapter

        cargarRestaurantesFavoritos()
    }

    private fun cargarRestaurantesFavoritos() {
        RestauranteManager.obtenerRestaurantesFavoritos(usuarioId) { favoritos ->
            restaurantesFavoritos.clear()
            restaurantesFavoritos.addAll(favoritos)
            favoritosAdapter.notifyDataSetChanged()
        }
    }

    private fun eliminarDeFavoritos(restauranteId: String) {
        RestauranteManager.eliminarRestauranteFavorito(usuarioId, restauranteId)
        cargarRestaurantesFavoritos()
        Toast.makeText(this, "Restaurante eliminado de favoritos", Toast.LENGTH_SHORT).show()
    }
}
