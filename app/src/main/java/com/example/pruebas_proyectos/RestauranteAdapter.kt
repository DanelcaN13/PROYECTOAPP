package com.example.pruebas_proyectos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebas_proyectos.databinding.ItemRestauranteBinding

class RestauranteAdapter(private val restaurantes: List<Restaurante>) :
    RecyclerView.Adapter<RestauranteAdapter.RestauranteViewHolder>() {

    inner class RestauranteViewHolder(private val binding: ItemRestauranteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurante: Restaurante) {
            binding.nombreRestaurante.text = restaurante.nombre
            binding.direccionRestaurante.text = restaurante.direccion
            binding.calificacionRestaurante.rating = restaurante.calificacion
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestauranteViewHolder {
        val binding = ItemRestauranteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestauranteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestauranteViewHolder, position: Int) {
        holder.bind(restaurantes[position])
    }

    override fun getItemCount(): Int = restaurantes.size
}
