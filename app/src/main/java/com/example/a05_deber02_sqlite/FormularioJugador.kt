package com.example.a05_deber02_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.example.a05_deber02_sqlite.BaseDatos.BaseDeDatos
import com.google.android.material.snackbar.Snackbar

class FormularioJugador : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_jugador)

        val title = intent.getStringExtra("titulo")
        val id = intent.getIntExtra("idJugador",0)
        val idEquipo = intent.getIntExtra("idEquipo",0)
        val tvTitle = findViewById<TextView>(R.id.tv_title_jugador)

        mostrarSnackbar(idEquipo.toString() + " " + id.toString())

        tvTitle.text = title.toString()

        val etID = findViewById<EditText>(R.id.et_id_jugador)
        val etIDequipo = findViewById<EditText>(R.id.et_id_equipo_jugador)
        val etNombre = findViewById<EditText>(R.id.et_nombre_jugador)
        val etNumero = findViewById<EditText>(R.id.et_numero_jugador)
        val etNacionalidad = findViewById<EditText>(R.id.et_nacionalidad_jugador)
        val swTitular  = findViewById<Switch>(R.id.sw_titular_jugador)

        if(id!=0){
            val jugador = BaseDeDatos.tablaJugador!!.consultarJugadorPorID(id)
            etID.setText(jugador.id.toString())
            etIDequipo.setText(jugador.idEquipo.toString())
            etNombre.setText(jugador.nombre.toString())
            etNumero.setText(jugador.numero.toString())
            etNacionalidad.setText(jugador.nacionalidad.toString())
            swTitular.isChecked=jugador.esTitular

        }
        else{
            etID.isEnabled = false
            etIDequipo.setText(idEquipo.toString())
        }

        val btnGuardar = findViewById<Button>(R.id.btn_guardar_jugador)
        btnGuardar
            .setOnClickListener { guardarDatos(id)}

    }

    fun guardarDatos(id: Int){
        val etID = findViewById<EditText>(R.id.et_id_jugador)
        val etIDequipo = findViewById<EditText>(R.id.et_id_equipo_jugador)
        val etNombre = findViewById<EditText>(R.id.et_nombre_jugador)
        val etNumero = findViewById<EditText>(R.id.et_numero_jugador)
        val etNacionalidad = findViewById<EditText>(R.id.et_nacionalidad_jugador)
        val swTitular  = findViewById<Switch>(R.id.sw_titular_jugador)


        val idEquipo = etIDequipo.text.toString().toInt()
        val nombre = etNombre.text.toString()
        val numero = etNumero.text.toString().toInt()
        val nacionalidad = etNacionalidad.text.toString()
        val titular = swTitular.isChecked()

        var result=false

        if(id==0){
            result = BaseDeDatos.tablaJugador!!.crearJugador(idEquipo,nombre,nacionalidad,numero,titular)
        }
        else{
            val idJugador = etID.text.toString().toInt()
            result = BaseDeDatos.tablaJugador!!.actualizarJugador(idJugador,idEquipo,nombre,nacionalidad,numero,titular)
        }
        if(result){
            setResult(
                RESULT_OK,
            )
            finish()
        }
        else{
            mostrarSnackbar("Error guardando datos, intenta nuevamente")
        }
    }
    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.ly_form_jugador),
                texto,
                Snackbar.LENGTH_LONG,
            )
            .show()
    }
}