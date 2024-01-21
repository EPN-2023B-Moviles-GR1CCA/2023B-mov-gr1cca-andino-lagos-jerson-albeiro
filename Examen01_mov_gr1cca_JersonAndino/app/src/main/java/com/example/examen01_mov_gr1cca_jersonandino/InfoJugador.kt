package com.example.examen01_mov_gr1cca_jersonandino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class InfoJugador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_jugador)

        val id = intent.getIntExtra("id",-1)

        if(id!=-1){
            val jugador = BDMemoria.jugadores.filter { it -> it.id==id }.first()
            val equipo = BDMemoria.equipos.filter { it -> it.id==jugador.idEquipo }.first()

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