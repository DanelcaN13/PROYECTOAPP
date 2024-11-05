package com.example.pruebas_proyectos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RestaurantesFavoritosAdapter(
    private val restaurantes: List<RestauranteManager.Restaurante>,
    private val onEliminarFavorito: (String) -> Unit
) : RecyclerView.Adapter<RestaurantesFavoritosAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView = view.findViewById(R.id.textViewNombreRestaurante)
        val direccionTextView: TextView = view.findViewById(R.id.textViewDireccion)
        val calificacionTextView: TextView = view.findViewById(R.id.textViewCalificacion)
        val rangoPrecioTextView: TextView = view.findViewById(R.id.textViewRangoPrecio)
        val eliminarButton: Button = view.findViewById(R.id.buttonEliminarFavorito)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurante_favorito, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurante = restaurantes[position]
        holder.nombreTextView.text = restaurante.nombre
        holder.direccionTextView.text = restaurante.direccion
        holder.calificacionTextView.text = "Calificación: ${restaurante.calificacion}"
        holder.rangoPrecioTextView.text = "Rango de Precio: ${restaurante.rangoPrecio}"

        // Acción de eliminar el restaurante de favoritos
        holder.eliminarButton.setOnClickListener {
            onEliminarFavorito(restaurante.idRestaurante)
        }
    }

    override fun getItemCount() = restaurantes.size
}
