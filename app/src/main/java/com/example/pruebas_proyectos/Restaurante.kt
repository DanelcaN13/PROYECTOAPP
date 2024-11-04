package com.example.pruebas_proyectos

data class Restaurante(
    var idRestaurante: String = "",   // ID del restaurante
    var nombre: String = "",          // Nombre del restaurante
    var direccion: String = "",       // Dirección del restaurante
    var calificacion: Int = 0,        // Calificación del restaurante
    var resena: String = ""           // Comentario del restaurante
)