package com.example.pruebas_proyectos

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class MyUsers(
    var idUsuario: String = "",
    var usuario: String,       // Nombre de usuario
    val nombre: String,        // Nombre del usuario
    val apellido: String,      // Apellido del usuario
    val correo: String,        // Correo electrónico
    val contrasena: String     // Contraseña (puedes considerar no guardar la contraseña en Firestore)
)

class Crearbd {

    companion object {
        private val db = FirebaseFirestore.getInstance()
        private val collectionName = "usuarios"

        // Método para agregar un registro de usuario a Firebase Firestore
        fun agregarRegistro(nombre: String, apellido: String, correo: String, contrasena: String, usuario: String) {
            val auth = FirebaseAuth.getInstance()

            // Crear un usuario en Firebase Authentication
            auth.createUserWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Usuario registrado exitosamente en Firebase Authentication
                        val idUsuario = auth.currentUser?.uid ?: ""
                        val usuarioData = MyUsers(
                            idUsuario = idUsuario,
                            usuario = usuario,   // Nombre de usuario
                            nombre = nombre,     // Nombre
                            apellido = apellido, // Apellido
                            correo = correo,     // Correo
                            contrasena = contrasena // Contraseña (evita guardar si es posible)
                        )

                        // Guardar el usuario en Firestore
                        db.collection(collectionName).document(idUsuario)
                            .set(usuarioData)
                            .addOnSuccessListener {
                                Log.d("Firestore", "Usuario registrado con ID: $idUsuario")
                            }
                            .addOnFailureListener { e ->
                                Log.w("Firestore", "Error al guardar el usuario en Firestore", e)
                            }
                    } else {
                        Log.w("FirebaseAuth", "Error en el registro de usuario", task.exception)
                    }
                }
        }

        // Método para verificar el inicio de sesión en Firebase Firestore
        fun iniciarSesion(usuario: String, contrasena: String, callback: (Boolean) -> Unit) {
            // Primero, obtenemos el documento del usuario por nombre de usuario
            db.collection(collectionName)
                .whereEqualTo("usuario", usuario)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful && !task.result.isEmpty) {
                        val document = task.result.documents[0]
                        val correo = document.getString("correo") // Obtener el correo

                        // Iniciar sesión con el correo obtenido
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(correo ?: "", contrasena)
                            .addOnCompleteListener { signInTask ->
                                if (signInTask.isSuccessful) {
                                    callback(true) // Sesión iniciada correctamente
                                } else {
                                    Log.w("FirebaseAuth", "Error en el inicio de sesión", signInTask.exception)
                                    callback(false) // Error en el inicio de sesión
                                }
                            }
                    } else {
                        Log.w("Firestore", "Error al obtener el usuario por nombre de usuario", task.exception)
                        callback(false) // Usuario no encontrado
                    }
                }
        }

        // Método para obtener los datos de un usuario desde Firestore
        fun obtenerUsuario(idUsuario: String, callback: (MyUsers?) -> Unit) {
            db.collection(collectionName).document(idUsuario)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val usuario = document.toObject(MyUsers::class.java)
                        callback(usuario) // Retorna el objeto usuario si se encuentra
                    } else {
                        Log.d("Firestore", "Usuario no encontrado con ID: $idUsuario")
                        callback(null) // Retorna null si no se encuentra el usuario
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error al obtener el usuario", e)
                    callback(null) // Retorna null en caso de error
                }
        }
    }
}

