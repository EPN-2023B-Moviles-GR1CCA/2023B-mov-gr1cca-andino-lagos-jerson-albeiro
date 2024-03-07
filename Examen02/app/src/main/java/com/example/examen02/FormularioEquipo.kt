package com.example.examen02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.examen02.Clases.Equipo
import com.example.examen02.Helpers.BaseDatos
import com.google.android.material.snackbar.Snackbar

class FormularioEquipo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_equipo)

        val titulo = intent.getStringExtra("titulo")
        val position = intent.getStringExtra("id")
        val tpTitulo = findViewById<TextView>(R.id.tv_title)
        //mostrarSnackbar(position.toString())

        if(titulo!=null)
            tpTitulo.setText(titulo.toString())

        val ptNombre = findViewById<EditText>(R.id.pt_nombre_equipo)
        val ptAnio = findViewById<EditText>(R.id.pt_anio_equipo)
        val ptDivision = findViewById<EditText>(R.id.pt_division_equipo)
        val ptDirector = findViewById<EditText>(R.id.pt_director_equipo)


        if(position!=null) {
            BaseDatos.BaseEquipo!!.obtenerEquipoByID(position)
                .addOnSuccessListener {
                    val equipo = Equipo(
                        it.id,
                        it.data!!["nombre"] as String,
                        it.data!!["aniocreacion"].toString().toInt(),
                        it.data!!["division"].toString(),
                        it.data!!["directortecnico"].toString()
                    )

                    ptNombre.setText(equipo.nombre)
                    ptAnio.setText(equipo.anioCreacion.toString())
                    ptDivision.setText(equipo.division)
                    ptDirector.setText(equipo.directorTecnico)
                }

        }

        val botonGuardar = findViewById<Button>(R.id.btn_guardar_equipo)
        botonGuardar
            .setOnClickListener {  guardarDatos(position)}
    }

    fun guardarDatos(id:String?){
        val ptNombre = findViewById<EditText>(R.id.pt_nombre_equipo)
        val ptAnio = findViewById<EditText>(R.id.pt_anio_equipo)
        val ptDivision = findViewById<EditText>(R.id.pt_division_equipo)
        val ptDirector = findViewById<EditText>(R.id.pt_director_equipo)


        val nombre=ptNombre.text.toString()
        val anio = ptAnio.text.toString().toInt()
        val division = ptDivision.text.toString().uppercase()
        val director = ptDirector.text.toString()

        var result=false;

        if(id==null)
            result = BaseDatos.BaseEquipo!!.crearEquipo(nombre,anio,division,director)
        else {
            result = BaseDatos.BaseEquipo!!.actualizarEquipo(id,nombre,anio,division,director)
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
        Snackbar.make(
            findViewById(R.id.ly_form_equipo),
            texto,
            Snackbar.LENGTH_LONG
        )
            .show()
    }
}