package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.pruebas_proyectos.databinding.ActivityFavoritosSemanalesBinding

class FavoritosSemanales : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritosSemanalesBinding
    private lateinit var adapter: RestaurantesSemanalesAdapter
    private val restaurantesSemanales = mutableListOf<Restaurante>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoritosSemanalesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el evento de clic para el botón "Regresar"
        binding.btnRegresar.setOnClickListener {
            val intent = Intent(this, CaraPublico::class.java)
            startActivity(intent)
            finish()  // Cierra la actividad actual
        }

        // Configurar el evento de clic para agregar un restaurante a los favoritos semanales
        binding.btnFavorito.setOnClickListener {
            mostrarFormularioAgregarRestaurante()
        }

        // Configurar el evento de clic para eliminar todos los restaurantes de los favoritos semanales
        binding.btnEliminarTodos.setOnClickListener {
            eliminarTodosLosRestaurantesSemanales()
        }

        obtenerRestaurantesSemanales()
    }

    private fun obtenerRestaurantesSemanales() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        // Obtener los restaurantes favoritos de la semana desde Firestore
        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document(userId)
            .collection("restaurantes_semanales")
            .get()
            .addOnSuccessListener { documents ->
                restaurantesSemanales.clear()  // Limpiar la lista antes de agregar los nuevos datos
                if (documents.isEmpty) {
                    binding.textViewNoFavoritos.visibility = View.VISIBLE
                } else {
                    binding.textViewNoFavoritos.visibility = View.GONE
                    for (document in documents) {
                        val restaurante = document.toObject(Restaurante::class.java)
                        restaurantesSemanales.add(restaurante)
                    }

                    // Configurar el RecyclerView con el adaptador
                    adapter = RestaurantesSemanalesAdapter(restaurantesSemanales)
                    binding.recyclerViewFavoritosSemanales.layoutManager = LinearLayoutManager(this)
                    binding.recyclerViewFavoritosSemanales.adapter = adapter
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar los favoritos", Toast.LENGTH_SHORT).show()
            }
    }

    private fun agregarRestauranteAFavoritosSemanales(restaurante: Restaurante) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        // Obtener la referencia a la colección de favoritos semanales en Firestore
        val favoritosRef = FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document(userId)
            .collection("restaurantes_semanales")

        // Agregar el restaurante a Firestore
        favoritosRef.add(restaurante)
            .addOnSuccessListener {
                Toast.makeText(this, "Restaurante agregado a favoritos semanales", Toast.LENGTH_SHORT).show()
                obtenerRestaurantesSemanales()  // Recargar la lista después de agregar el restaurante
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al agregar el restaurante", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
    }

    // Función para mostrar un formulario de entrada para agregar un restaurante
    private fun mostrarFormularioAgregarRestaurante() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Agregar Restaurante a Favoritos")

        // Crear un Layout con dos campos EditText (nombre y dirección)
        val layout = layoutInflater.inflate(R.layout.dialog_agregar_restaurante, null)
        val nombreEditText = layout.findViewById<EditText>(R.id.editTextNombre)
        val direccionEditText = layout.findViewById<EditText>(R.id.editTextDireccion)

        builder.setView(layout)

        builder.setPositiveButton("Agregar") { _, _ ->
            val nombre = nombreEditText.text.toString()
            val direccion = direccionEditText.text.toString()

            if (nombre.isNotEmpty() && direccion.isNotEmpty()) {
                val restaurante = Restaurante(nombre, direccion, 4.5f, "Sin reseña")
                agregarRestauranteAFavoritosSemanales(restaurante)
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancelar", null)

        builder.show()
    }

    // Función para eliminar todos los restaurantes de los favoritos semanales
    private fun eliminarTodosLosRestaurantesSemanales() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        // Obtener la referencia a la colección de favoritos semanales en Firestore
        val favoritosRef = FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document(userId)
            .collection("restaurantes_semanales")

        // Eliminar todos los documentos en la colección
        favoritosRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    favoritosRef.document(document.id).delete()  // Eliminar cada documento
                }
                Toast.makeText(this, "Todos los restaurantes han sido eliminados", Toast.LENGTH_SHORT).show()
                obtenerRestaurantesSemanales()  // Recargar la lista después de eliminar los restaurantes
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al eliminar los restaurantes", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
    }
}
