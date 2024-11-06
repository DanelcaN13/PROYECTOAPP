package com.example.pruebas_proyectos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.pruebas_proyectos.databinding.ActivityDescuentosBinding
import android.view.Menu
import android.view.MenuItem

// Cambiar a heredar de BaseActivity en lugar de AppCompatActivity
class Descuentos : BaseActivity() {

    private lateinit var binding: ActivityDescuentosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflar el layout y configurar el binding
        binding = ActivityDescuentosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar insets para la vista principal
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar el evento de clic para el botón "Regresar"
        binding.btnRegresar.setOnClickListener {
            finish() // Redirigir a la actividad anterior
        }

        // Configurar el evento de clic para el botón "Revisar Ofertas"
        binding.btnRevisarOpinion.setOnClickListener {
            val intent = Intent(this, DescuentosTerminados::class.java)
            startActivity(intent)
        }

        // Configurar Bottom Navigation (si es necesario para esta actividad)
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, CaraPublico::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_favorites -> {
                    val intent = Intent(this, FavoritosSemanales::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_profile -> {
                    val intent = Intent(this, Perfil::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    // Inflar el menú de la barra de navegación (si lo necesitas)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu) // Asegúrate de que el archivo XML del menú esté en res/menu/
        return true
    }

    // Gestionar la selección de los elementos del menú de la barra de navegación (si lo necesitas)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_home -> {
                val intent = Intent(this, CaraPublico::class.java)
                startActivity(intent)
                true
            }
            R.id.nav_favorites -> {
                val intent = Intent(this, FavoritosSemanales::class.java)
                startActivity(intent)
                true
            }
            R.id.nav_profile -> {
                val intent = Intent(this, Perfil::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
