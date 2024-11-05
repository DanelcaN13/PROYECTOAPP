package com.example.pruebas_proyectos

data class Restaurante(
    val nombre: String = "",
    val direccion: String = "",
    val calificacion: Float = 0f,
    val resena: String = "",
    val rangoPrecio: String = ""
)

data class Usuario(
    val id: String = "",
    val nombre: String = "",
    val restaurantesFavoritos: List<String> = listOf() // Lista de IDs de restaurantes favoritos
)
