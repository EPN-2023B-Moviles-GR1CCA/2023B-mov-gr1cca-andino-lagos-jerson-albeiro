package com.example.gr1accjaal2023b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class ECrudEntrenador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecrud_entrenador)

        val botonBuscar = findViewById<Button>(R.id.btn_buscar)
        botonBuscar.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)

            val entrenador = EBaseDeDatos.tablaEntrenador!!
                .consultarEntrenadorPorID(
                    id.text.toString().toInt()
                )

            if (entrenador.id == 0)
                mostrarSnackbar("Usuario no encontrado")
            else {
                id.setText(entrenador.id.toString())
                nombre.setText(entrenador.nombre.toString())
                descripcion.setText(entrenador.descripcion.toString())
                mostrarSnackbar("Usuario encontrado")
            }
        }
        val botonCrear = findViewById<Button>(R.id.btn_crear)
        botonCrear.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val respuesta = EBaseDeDatos.tablaEntrenador!!.crearEntrenador(
                nombre.text.toString(),
                descripcion.text.toString()
            )
            if (respuesta) mostrarSnackbar("Entrenador Creado")
        }

        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar)
        botonActualizarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val respuesta = EBaseDeDatos.tablaEntrenador!!.actualizarEntrenadorFormulario(
                nombre.text.toString(),
                descripcion.text.toString(),
                id.text.toString().toInt()
            )
            if (respuesta) mostrarSnackbar("Entrenador Actualizado")
        }

        val botonEliminarBDD = findViewById<Button>(R.id.btn_eliminar)
        botonEliminarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val respuesta = EBaseDeDatos.tablaEntrenador!!.eliminarEntrenadorFormulario(
                id.text.toString().toInt()
            )
            if (respuesta) mostrarSnackbar("Entrenador Eliminado")
        }
    }
    fun mostrarSnackbar(texto:String){
        Snackbar.make(
            findViewById(R.id.cl_sqlite),
            texto,
            Snackbar.LENGTH_LONG
        )
            .show()
    }
}