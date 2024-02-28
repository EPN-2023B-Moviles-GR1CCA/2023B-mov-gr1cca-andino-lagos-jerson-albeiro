package com.example.deber02_sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.deber02_sqlite.BaseDatos.BaseDeDatos
import com.google.android.material.snackbar.Snackbar

class FormularioEquipo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_equipo)

        val titulo = intent.getStringExtra("titulo")
        val position = intent.getIntExtra("position",0)
        val tpTitulo = findViewById<TextView>(R.id.tv_title)
        mostrarSnackbar(position.toString())

        if(titulo!=null)
            tpTitulo.setText(titulo.toString())

        val ptID = findViewById<EditText>(R.id.pt_id_equipo)
        val ptNombre = findViewById<EditText>(R.id.pt_nombre_equipo)
        val ptAnio = findViewById<EditText>(R.id.pt_anio_equipo)
        val ptDivision = findViewById<EditText>(R.id.pt_division_equipo)
        val ptDirector = findViewById<EditText>(R.id.pt_director_equipo)


        if(position!=0) {
            val equipo = BaseDeDatos.tablaEquipo!!.consultarEquipoPorID(position)
            ptID.setText(equipo.id.toString())
            ptNombre.setText(equipo.nombre.toString())
            ptAnio.setText(equipo.anioCreacion.toString())
            ptDivision.setText(equipo.division.toString())
            ptDirector.setText(equipo.directorTecnico.toString())
        }
        else{
            ptID.isEnabled = false
        }

        val botonGuardar = findViewById<Button>(R.id.btn_guardar_equipo)
        botonGuardar
            .setOnClickListener {  guardarDatos(position)}
    }
    fun guardarDatos(position:Int){
        val ptID = findViewById<EditText>(R.id.pt_id_equipo)
        val ptNombre = findViewById<EditText>(R.id.pt_nombre_equipo)
        val ptAnio = findViewById<EditText>(R.id.pt_anio_equipo)
        val ptDivision = findViewById<EditText>(R.id.pt_division_equipo)
        val ptDirector = findViewById<EditText>(R.id.pt_director_equipo)


        val nombre=ptNombre.text.toString()
        val anio = ptAnio.text.toString().toInt()
        val division = ptDivision.text.toString().uppercase()
        val director = ptDirector.text.toString()

        var result=false;

        if(position==0)
            result = BaseDeDatos.tablaEquipo!!.crearEquipo(nombre,anio,division,director)
        else {
            val id = ptID.text.toString().toInt()
            result = BaseDeDatos.tablaEquipo!!.actualizarEquipo(id,nombre,anio,division,director)
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
    fun devolverRespuesta(){
        val intentDevolverParametros = Intent()

        intentDevolverParametros.putExtra("position", 22)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }
    fun mostrarSnackbar(texto:String){
        Snackbar.make(
            findViewById(R.id.ly_form_equipo),
            texto,
            Snackbar.LENGTH_LONG
        )
            .show()
    }
}