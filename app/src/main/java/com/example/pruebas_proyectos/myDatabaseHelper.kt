package com.example.pruebas_proyectos

import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mi_base_de_datos.db" // Nombre de tu base de datos
        private const val DATABASE_VERSION = 1 // Versión de la base de datos
        const val TABLE_NAME = "usuarios" // Cambiado a 'const val' para que sea accesible
    }

    // Método que se llama al crear la base de datos
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT, "
                + "apellido TEXT, "
                + "correo TEXT, "
                + "genero TEXT, "
                + "usuario TEXT, "
                + "contrasena TEXT)")
        db.execSQL(createTable)
    }

    // Método que se llama cuando se actualiza la base de datos
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Método para agregar un usuario
    fun agregarUsuario(nombre: String, apellido: String, correo: String, genero: String, usuario: String, contrasena: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("nombre", nombre)
        values.put("apellido", apellido)
        values.put("correo", correo)
        values.put("genero", genero)
        values.put("usuario", usuario)
        values.put("contrasena", contrasena)

        val resultado = db.insert(TABLE_NAME, null, values)
        db.close()

        // Log para verificar el resultado de la inserción
        if (resultado == -1L) {
            Log.e("MyDatabaseHelper", "Error al insertar el usuario: $usuario")
        } else {
            Log.d("MyDatabaseHelper", "Usuario insertado correctamente: $usuario")
            Log.d("MyDatabaseHelper", "Intentando agregar usuario: $usuario")
        }
    }


    // Método para obtener todos los usuarios (puedes modificarlo según tus necesidades)
    fun obtenerUsuarios(): List<String> {
        val usuarios = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val nombreIndex = cursor.getColumnIndex("nombre")
                val apellidoIndex = cursor.getColumnIndex("apellido")
                val correoIndex = cursor.getColumnIndex("correo")

                // Comprobar que los índices no sean -1
                if (nombreIndex != -1 && apellidoIndex != -1 && correoIndex != -1) {
                    val nombre = cursor.getString(nombreIndex)
                    val apellido = cursor.getString(apellidoIndex)
                    val correo = cursor.getString(correoIndex)
                    // Agrega más campos si es necesario
                    usuarios.add("$nombre $apellido - $correo") // Formato de salida
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return usuarios
    }

    // Método para verificar si un usuario existe
    fun verificarUsuario(usuario: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE usuario = ?", arrayOf(usuario))

        val existe = cursor.count > 0 // Si hay registros, el usuario existe
        cursor.close()
        db.close()

        return existe
    }
}

