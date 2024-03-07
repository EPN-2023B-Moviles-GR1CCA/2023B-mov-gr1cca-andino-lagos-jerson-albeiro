package com.example.examen02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.example.examen02.Helpers.BaseDatos
import com.google.android.material.snackbar.Snackbar

class FormularioJugador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_jugador)

        val title = intent.getStringExtra("titulo")
        val id_jugador = intent.getStringExtra("idJugador")
        var id_equipo = intent.getStringExtra("idEquipo")
        val tvTitle = findViewById<TextView>(R.id.tv_title_jugador)

        //mostrarSnackbar(id_jugador + id_equipo)
        tvTitle.text = title.toString()

        val etNombre = findViewById<EditText>(R.id.et_nombre_jugador)
        val etNumero = findViewById<EditText>(R.id.et_numero_jugador)
        val etNacionalidad = findViewById<EditText>(R.id.et_nacionalidad_jugador)
        val swTitular = findViewById<Switch>(R.id.sw_titular_jugador)

        if (id_jugador != null) {
            BaseDatos.BaseJugador!!.obtenerJugadorByID(id_jugador)
                .addOnSuccessListener {
                    val etNombre = findViewById<EditText>(R.id.et_nombre_jugador)
                    val etNumero = findViewById<EditText>(R.id.et_numero_jugador)
                    val etNacionalidad = findViewById<EditText>(R.id.et_nacionalidad_jugador)
                    val swTitular = findViewById<Switch>(R.id.sw_titular_jugador)

                    etNombre.setText(it.data!!["nombre"].toString())
                    etNumero.setText(it.data!!["numero"].toString())
                    etNacionalidad.setText(it.data!!["nacionalidad"].toString())
                    swTitular.isChecked = it.data!!["estitular"].toString().toBoolean()
                }


        }

        if (id_equipo == null)
            id_equipo = "test"

        val btnGuardar = findViewById<Button>(R.id.btn_guardar_jugador)
        btnGuardar
            .setOnClickListener { guardarDatos(id_jugador, id_equipo) }

    }

    fun guardarDatos(id_jugador: String?, id_equipo: String) {
        val etNombre = findViewById<EditText>(R.id.et_nombre_jugador)
        val etNumero = findViewById<EditText>(R.id.et_numero_jugador)
        val etNacionalidad = findViewById<EditText>(R.id.et_nacionalidad_jugador)
        val swTitular = findViewById<Switch>(R.id.sw_titular_jugador)


        val nombre = etNombre.text.toString()
        val numero = etNumero.text.toString().toInt()
        val nacionalidad = etNacionalidad.text.toString()
        val titular = swTitular.isChecked()

        var result = false

        if (id_jugador == null) {
            result = BaseDatos.BaseJugador!!.crearJugador(
                id_equipo,
                nombre,
                nacionalidad,
                numero,
                titular
            )
        } else {
                result = BaseDatos.BaseJugador!!.actualizarJugador(
                    id_jugador,
                    id_equipo,
                    nombre,
                    nacionalidad,
                    numero,
                    titular
                )
        }
        if (result) {
            setResult(
                RESULT_OK,
            )
            finish()
        } else {
            mostrarSnackbar("Error guardando datos, intenta nuevamente")
        }
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.ly_form_jugador),
                texto,
                Snackbar.LENGTH_LONG,
            )
            .show()
    }
}