package com.example.examen01_mov_gr1cca_jersonandino

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Switch
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class FormularioJugador : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_jugador)

        val title = intent.getStringExtra("titulo")
        val id = intent.getIntExtra("idJugador",-1)
        val idEquipo = intent.getIntExtra("idEquipo",0)
        val tvTitle = findViewById<TextView>(R.id.tv_title_jugador)

        tvTitle.text = title.toString()

        val etID = findViewById<EditText>(R.id.et_id_jugador)
        val etIDequipo = findViewById<EditText>(R.id.et_id_equipo_jugador)
        val etNombre = findViewById<EditText>(R.id.et_nombre_jugador)
        val etNumero = findViewById<EditText>(R.id.et_numero_jugador)
        val etNacionalidad = findViewById<EditText>(R.id.et_nacionalidad_jugador)
        val swTitular  = findViewById<Switch>(R.id.sw_titular_jugador)

        if(id!=-1){
            val jugador = BDMemoria.jugadores.filter { it -> it.id == id }.first()
            etID.setText(jugador.id.toString())
            etIDequipo.setText(jugador.idEquipo.toString())
            etNombre.setText(jugador.nombre.toString())
            etNumero.setText(jugador.numero.toString())
            etNacionalidad.setText(jugador.nacionalidad.toString())
            swTitular.isChecked=jugador.esTitular

        }
        else{
            etID.setText(BDMemoria.calcularIdNuevoJugador().toString())
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

        val idJugador = etID.text.toString().toInt()
        val idEquipo = etIDequipo.text.toString().toInt()
        val nombre = etNombre.text.toString()
        val numero = etNumero.text.toString().toInt()
        val nacionalidad = etNacionalidad.text.toString()
        val titular = swTitular.isChecked()

        var result=false

        if(id==-1){
            result = BDMemoria.crearJugador(idEquipo, nombre,nacionalidad,numero,titular)
        }
        else{
            BDMemoria.editarJugador(idJugador,idEquipo,nombre,nacionalidad,numero,titular)
            result=true
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