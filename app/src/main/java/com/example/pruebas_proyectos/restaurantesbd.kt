package com.example.pruebas_proyectos

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class RestauranteManager {

    data class Restaurante(
        var idRestaurante: String = "",   // ID del restaurante
        var nombre: String,                // Nombre del restaurante
        var direccion: String,             // Dirección del restaurante
        var calificacion: Int,             // Calificación del restaurante
        var resena: String,                // Comentario del restaurante
        var rangoPrecio: String            // Rango de precios del restaurante
    )

    companion object {
        private val db = FirebaseFirestore.getInstance()
        private val collectionName = "restaurantes"

        // Método para agregar un restaurante a Firestore
        fun agregarRestaurante(nombre: String, direccion: String, calificacion: Int, resena: String, rangoPrecio: String) {
            val nuevoRestaurante = Restaurante(
                idRestaurante = db.collection(collectionName).document().id,
                nombre = nombre,
                direccion = direccion,
                calificacion = calificacion,
                resena = resena,
                rangoPrecio = rangoPrecio
            )

            // Guardar el restaurante en Firestore
            db.collection(collectionName).document(nuevoRestaurante.idRestaurante)
                .set(nuevoRestaurante)
                .addOnSuccessListener {
                    Log.d("Firestore", "Restaurante agregado con ID: ${nuevoRestaurante.idRestaurante}")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error al agregar el restaurante: ${e.message}", e)
                }
        }

        // Método para obtener todos los restaurantes de Firestore
        fun obtenerRestaurantes(callback: (List<Restaurante>) -> Unit) {
            db.collection(collectionName)
                .get()
                .addOnSuccessListener { result ->
                    val restaurantes = mutableListOf<Restaurante>()
                    for (document in result) {
                        val restaurante = document.toObject(Restaurante::class.java)
                        restaurantes.add(restaurante)
                    }
                    callback(restaurantes)
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error al obtener los restaurantes: ${e.message}", e)
                    callback(emptyList())
                }
        }

        // Método para obtener un restaurante específico por su ID
        fun obtenerRestaurantePorId(id: String, callback: (Restaurante?) -> Unit) {
            db.collection(collectionName).document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val restaurante = document.toObject(Restaurante::class.java)
                        callback(restaurante)
                    } else {
                        Log.d("Firestore", "No se encontró el restaurante con ID: $id")
                        callback(null)
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error al obtener el restaurante: ${e.message}", e)
                    callback(null)
                }
        }
    }
}
