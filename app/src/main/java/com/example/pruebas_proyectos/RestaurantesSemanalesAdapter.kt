package com.example.pruebas_proyectos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RestaurantesSemanalesAdapter(private val restaurantes: List<Restaurante>) :
    RecyclerView.Adapter<RestaurantesSemanalesAdapter.RestauranteViewHolder>() {

    class RestauranteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.textViewNombre)
        val direccion: TextView = itemView.findViewById(R.id.textViewDireccion)
        val calificacion: TextView = itemView.findViewById(R.id.textViewCalificacion)
        val btnFavorito: ImageButton = itemView.findViewById(R.id.btn_favorito)  // Botón de favorito
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestauranteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurante_favorito, parent, false)
        return RestauranteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestauranteViewHolder, position: Int) {
        val restaurante = restaurantes[position]
        holder.nombre.text = restaurante.nombre
        holder.direccion.text = restaurante.direccion
        holder.calificacion.text = "Calificación: ${restaurante.calificacion}"

        // Manejo del clic en el botón de "Favorito"
        holder.btnFavorito.setOnClickListener {
            // Agregar el restaurante a los favoritos semanales en Firestore
            agregarRestauranteAFavoritosSemanales(restaurante)
        }
    }

    override fun getItemCount(): Int {
        return restaurantes.size
    }

    // Método para agregar un restaurante a los favoritos semanales
    private fun agregarRestauranteAFavoritosSemanales(restaurante: Restaurante) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        // Referencia a la colección de restaurantes semanales
        val db = FirebaseFirestore.getInstance()
        val restauranteRef = db.collection("usuarios")
            .document(userId)
            .collection("restaurantes_semanales")

        // Agregar el restaurante a Firestore
        restauranteRef.add(restaurante)
            .addOnSuccessListener {
                // Puedes cambiar el ícono de "Favorito" o mostrar un mensaje de éxito
                // Por ejemplo, un Toast o cambiar el ícono a un estado de "favorito"
                // Toast.makeText(holder.itemView.context, "Restaurante agregado a favoritos", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                // Mostrar un mensaje de error si ocurre algún problema al agregar el restaurante
                // Toast.makeText(holder.itemView.context, "Error al agregar a favoritos", Toast.LENGTH_SHORT).show()
            }
    }
}
