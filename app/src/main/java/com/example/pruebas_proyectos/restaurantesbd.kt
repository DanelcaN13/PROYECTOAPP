package com.example.pruebas_proyectos

import android.util.Log
import com.google.firebase.firestore.FieldValue
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

        // Método para agregar un restaurante a la lista de favoritos del usuario
        fun agregarRestauranteFavorito(usuarioId: String, restauranteId: String) {
            val usuarioRef = db.collection("usuarios").document(usuarioId)

            usuarioRef.update("restaurantesFavoritos", FieldValue.arrayUnion(restauranteId))
                .addOnSuccessListener {
                    Log.d("Firestore", "Restaurante $restauranteId agregado a favoritos para el usuario $usuarioId")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error al agregar restaurante a favoritos: ${e.message}", e)
                }
        }

        // Método para eliminar un restaurante de la lista de favoritos del usuario
        fun eliminarRestauranteFavorito(usuarioId: String, restauranteId: String) {
            val usuarioRef = db.collection("usuarios").document(usuarioId)

            usuarioRef.update("restaurantesFavoritos", FieldValue.arrayRemove(restauranteId))
                .addOnSuccessListener {
                    Log.d("Firestore", "Restaurante $restauranteId eliminado de favoritos para el usuario $usuarioId")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error al eliminar restaurante de favoritos: ${e.message}", e)
                }
        }

        // Método para obtener los restaurantes favoritos de un usuario
        fun obtenerRestaurantesFavoritos(usuarioId: String, callback: (List<Restaurante>) -> Unit) {
            val usuarioRef = db.collection("usuarios").document(usuarioId)

            usuarioRef.get().addOnSuccessListener { document ->
                if (document != null) {
                    val restaurantesFavoritos = document.get("restaurantesFavoritos") as? List<String> ?: emptyList()
                    val restaurantesList = mutableListOf<Restaurante>()

                    // Obtener los restaurantes favoritos
                    for (id in restaurantesFavoritos) {
                        obtenerRestaurantePorId(id) { restaurante ->
                            restaurante?.let { restaurantesList.add(it) }
                            if (restaurantesList.size == restaurantesFavoritos.size) {
                                callback(restaurantesList)
                            }
                        }
                    }
                } else {
                    Log.d("Firestore", "No se encontró el usuario con ID: $usuarioId")
                    callback(emptyList())
                }
            }.addOnFailureListener { e ->
                Log.w("Firestore", "Error al obtener el usuario: ${e.message}", e)
                callback(emptyList())
            }
        }
    }
}
