package com.example.pruebas_proyectos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RestauranteAdapter(private val listaRestaurantes: List<Restaurante>) : RecyclerView.Adapter<RestauranteAdapter.RestauranteViewHolder>() {

    class RestauranteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreRestaurante)
        val direccionTextView: TextView = itemView.findViewById(R.id.direccionRestaurante)
        val calificacionRatingBar: RatingBar = itemView.findViewById(R.id.calificacionRestaurante)
        val resenaTextView: TextView = itemView.findViewById(R.id.editTextResena)
        val rangoPreciosTextView: TextView = itemView.findViewById(R.id.rangoPreciosRestaurante) // Asegúrate de que este ID sea correcto
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestauranteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurante, parent, false)
        return RestauranteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestauranteViewHolder, position: Int) {
        val restaurante = listaRestaurantes[position]
        holder.nombreTextView.text = restaurante.nombre
        holder.direccionTextView.text = restaurante.direccion
        holder.calificacionRatingBar.rating = restaurante.calificacion
        holder.resenaTextView.text = restaurante.resena
        holder.rangoPreciosTextView.text = "Rango de precios: ${restaurante.rangoPrecio}" // Modificado para usar rangoPrecio
    }

    override fun getItemCount(): Int {
        return listaRestaurantes.size
    }
}

