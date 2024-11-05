package com.example.pruebas_proyectos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Modelo de datos para cada oferta
data class Oferta(val nombreRestaurante: String, val rangoPrecios: String)

// Adaptador para el RecyclerView de ofertas
class OfertaAdapter(private val listaOfertas: List<Oferta>) : RecyclerView.Adapter<OfertaAdapter.OfertaViewHolder>() {

    class OfertaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreRestauranteTextView: TextView = itemView.findViewById(R.id.text_nombre_restaurante)
        val rangoPreciosTextView: TextView = itemView.findViewById(R.id.text_rango_precio_oferta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfertaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_oferta, parent, false)
        return OfertaViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfertaViewHolder, position: Int) {
        val oferta = listaOfertas[position]
        holder.nombreRestauranteTextView.text = oferta.nombreRestaurante
        holder.rangoPreciosTextView.text = "Rango de precios: ${oferta.rangoPrecios}"
    }

    override fun getItemCount(): Int {
        return listaOfertas.size
    }
}
