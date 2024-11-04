package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.util.Log


class Perfil : AppCompatActivity() {

    private lateinit var inputUsuario: EditText
    private lateinit var inputNombre: EditText
    private lateinit var inputApellido: EditText
    private lateinit var inputContrasenaActual: EditText // Nuevo EditText para la contraseña actual
    private lateinit var inputContrasena: EditText
    private lateinit var btnGuardar: TextView

    private val auth = FirebaseAuth.getInstance()
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        // Inicializar campos
        inputUsuario = findViewById(R.id.input_usuario)
        inputNombre = findViewById(R.id.input_nombre)
        inputApellido = findViewById(R.id.input_apellido)
        inputContrasenaActual = findViewById(R.id.input_contrasena_actual) // Inicializa el EditText
        inputContrasena = findViewById(R.id.input_contrasena)
        btnGuardar = findViewById(R.id.guardar)

        database = FirebaseDatabase.getInstance().reference

        btnGuardar.setOnClickListener {
            val usuario = inputUsuario.text.toString()
            val nombre = inputNombre.text.toString()
            val apellido = inputApellido.text.toString()
            val contrasenaActual = inputContrasenaActual.text.toString() // Obtén la contraseña actual
            val nuevaContrasena = inputContrasena.text.toString()

            // Validar campos
            if (usuario.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || contrasenaActual.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Reautenticar al usuario para cambiar la contraseña
            val user = auth.currentUser
            if (user != null) {
                val credential = EmailAuthProvider.getCredential(user.email!!, contrasenaActual)

                user.reauthenticate(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Cambiar la contraseña
                        user.updatePassword(nuevaContrasena).addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                Toast.makeText(this, "Contraseña actualizada con éxito", Toast.LENGTH_SHORT).show()

                                // Guardar la información del usuario en Firestore
                                val usuarioData = MyUsers(
                                    idUsuario = user.uid, // Asegúrate de usar el ID de usuario
                                    usuario = usuario,
                                    nombre = nombre,
                                    apellido = apellido,
                                    correo = user.email!!,
                                    contrasena = nuevaContrasena // Guarda la nueva contraseña (considera las implicaciones de seguridad)
                                )

                                // Aquí guardamos el usuario en Firestore
                                Crearbd.db.collection(Crearbd.collectionName).document(user.uid)
                                    .set(usuarioData)
                                    .addOnSuccessListener {
                                        Log.d("Firestore", "Datos del usuario actualizados con éxito")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w("Firestore", "Error al actualizar datos del usuario", e)
                                    }

                                // Redirigir a inicio de sesión
                                val intent = Intent(this, Inicio::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Error al actualizar la contraseña", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
