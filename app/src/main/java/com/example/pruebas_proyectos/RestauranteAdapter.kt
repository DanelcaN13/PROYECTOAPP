package com.example.pruebas_proyectos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RestauranteAdapter(private val listaRestaurantes: List<Restaurante>) :
    RecyclerView.Adapter<RestauranteAdapter.RestauranteViewHolder>() {

    inner class RestauranteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreRestaurante)
        val direccionTextView: TextView = itemView.findViewById(R.id.direccionRestaurante)
        val calificacionRatingBar: RatingBar = itemView.findViewById(R.id.calificacionRestaurante)
        val resenaTextView: TextView = itemView.findViewById(R.id.editTextResena) // Referencia al TextView de la reseña
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestauranteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurante, parent, false)
        return RestauranteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestauranteViewHolder, position: Int) {
        val restaurante = listaRestaurantes[position]
        holder.nombreTextView.text = restaurante.nombre
        holder.direccionTextView.text = restaurante.direccion
        holder.calificacionRatingBar.rating = restaurante.calificacion.toFloat()
        holder.resenaTextView.text = restaurante.resena // Asignar la reseña al TextView
    }

    override fun getItemCount(): Int = listaRestaurantes.size
}
