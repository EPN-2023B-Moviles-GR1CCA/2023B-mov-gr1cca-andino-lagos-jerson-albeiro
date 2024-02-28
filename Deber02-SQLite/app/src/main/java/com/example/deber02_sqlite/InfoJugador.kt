package com.example.deber02_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.deber02_sqlite.BaseDatos.BaseDeDatos
import com.google.android.material.snackbar.Snackbar

class InfoJugador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_jugador)

        val id = intent.getIntExtra("id",0)

        if(id!=0){
            val jugador = BaseDeDatos.tablaJugador!!.consultarJugadorPorID(id)
            val equipo = BaseDeDatos.tablaEquipo!!.consultarEquipoPorID(jugador.idEquipo)

            val tvNombre = findViewById<TextView>(R.id.tv_nombre_jugador)
            val tvNacionalidad = findViewById<TextView>(R.id.tv_nacionalidad_jugador)
            val tvNumero = findViewById<TextView>(R.id.tv_numero_jugador)
            val tvTitular = findViewById<TextView>(R.id.tv_titular_jugador)
            val tvEquipo = findViewById<TextView>(R.id.tv_equipo_jugador)

            tvNombre.setText("Nombre: "+jugador.nombre.toString())
            tvNacionalidad.setText("Nacionalidad: "+jugador.nacionalidad.toString())
            tvNumero.setText("Numero: "+jugador.numero.toString())
            tvTitular.setText("Titular: "+if(jugador.esTitular) "SI" else "NO")
            tvEquipo.setText("Equipo: "+equipo.nombre.toString())
        }
        else{
            mostrarSnackbar("Error al leer los datos")
        }
    }
    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.lv_jugadores),
                texto,
                Snackbar.LENGTH_LONG,
            )
            .show()
    }
}