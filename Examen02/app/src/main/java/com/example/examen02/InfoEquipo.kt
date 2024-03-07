package com.example.examen02

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.examen02.Clases.Equipo
import com.example.examen02.Clases.Jugador
import com.example.examen02.Helpers.BaseDatos
import com.google.android.material.snackbar.Snackbar

class InfoEquipo : AppCompatActivity() {
    var jugadores = arrayListOf<Jugador>()
    val callbackEditarJugador =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if (result.resultCode == Activity.RESULT_OK){
                mostrarSnackbar("Datos Guardados")
                actualizarListView()
            }
        }

    val callbackDetallesJugador =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if (result.resultCode == Activity.RESULT_OK){
                actualizarListView()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_equipo)
        val position = intent.getStringExtra("id")
        //mostrarSnackbar(position.toString())

        if (position!=null)
            BaseDatos.BaseEquipo!!.obtenerEquipoByID(position).addOnSuccessListener {
                val tvNombre = findViewById<TextView>(R.id.tv_nombre_equipo)
                val tvAnio = findViewById<TextView>(R.id.tv_anio_equipo)
                val tvDivision = findViewById<TextView>(R.id.tv_division_equipo)
                val tvDirector = findViewById<TextView>(R.id.tv_director_equipo)

                val equipo = Equipo(
                    it.id,
                    it.data!!["nombre"] as String,
                    it.data!!["aniocreacion"].toString().toInt(),
                    it.data!!["division"].toString(),
                    it.data!!["directortecnico"].toString()
                )
                if(equipo != null){
                    tvNombre.text = "Nombre: ${equipo.nombre.toString()}"
                    tvDirector.text = "Director: ${equipo.directorTecnico.toString()}"
                    tvAnio.text = "Año: ${equipo.anioCreacion.toString()}"
                    tvDivision.text = "Division: ${equipo.division.toString()}"
                }
            }
        val btnNuevoJugador = findViewById<Button>(R.id.btn_nuevo_jugador)
        btnNuevoJugador
            .setOnClickListener {
                lanzarIntentEditarJugador("CREAR JUGADOR", null, position)
            }
        actualizarListView()
    }

    var posicionItemSeleccionado = 0
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_interactivo ,menu)
        //Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_equipo_editar -> {
                val position = intent.getStringExtra("id")
                if (position!=null)
                BaseDatos.BaseJugador!!.obtenerJugadores(position)
                    .addOnSuccessListener {
                        val jugadores= arrayListOf <Jugador>()
                        for (document in it) {
                            jugadores.add(
                                Jugador(
                                    document.id,
                                    document.data["idequipo"].toString(),
                                    document.data["nombre"].toString(),
                                    document.data["nacionalidad"].toString(),
                                    document.data["numero"].toString().toInt(),
                                    document.data["estitular"].toString().toBoolean()
                                )
                            )
                        }
                        val id_jugador = jugadores[posicionItemSeleccionado].id
                        val id_equipo = jugadores[posicionItemSeleccionado].idEquipo
                        lanzarIntentEditarJugador("EDITAR EQUIPO",id_jugador,id_equipo)
                    }
                return true
            }
            R.id.mi_equipo_eliminar -> {
                abrirDialogo()
                return true
            }
            R.id.mi_equipo_detalles -> {
                val position = intent.getStringExtra("id")
                if (position!=null)
                    BaseDatos.BaseJugador!!.obtenerJugadores(position)
                        .addOnSuccessListener {
                            val jugadores= arrayListOf <Jugador>()
                            for (document in it) {
                                jugadores.add(
                                    Jugador(
                                        document.id,
                                        document.data["idequipo"].toString(),
                                        document.data["nombre"].toString(),
                                        document.data["nacionalidad"].toString(),
                                        document.data["numero"].toString().toInt(),
                                        document.data["estitular"].toString().toBoolean()
                                    )
                                )
                            }
                            val id_jugador = jugadores[posicionItemSeleccionado].id
                            val id_equipo = jugadores[posicionItemSeleccionado].idEquipo
                            lanzarIntentEditarJugador("EDITAR EQUIPO",id_jugador,id_equipo)
                        }
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun lanzarIntentEditarJugador(titulo:String, id:String?, idEquipo:String?){
        val intentEditarJugador = Intent(this, FormularioJugador::class.java)
        intentEditarJugador.putExtra("titulo",titulo)
        intentEditarJugador.putExtra("idJugador",id)
        intentEditarJugador.putExtra("idEquipo",idEquipo)
        callbackEditarJugador.launch(intentEditarJugador)
    }
    fun abrirDialogo(){
        val position = intent.getStringExtra("id")
        if (position!=null)
        BaseDatos.BaseJugador!!.obtenerJugadores(position)
            .addOnSuccessListener {
                val jugadores= arrayListOf <Jugador>()
                for (document in it) {
                    jugadores.add(
                        Jugador(
                            document.id,
                            document.data["idequipo"].toString(),
                            document.data["nombre"].toString(),
                            document.data["nacionalidad"].toString(),
                            document.data["numero"].toString().toInt(),
                            document.data["estitular"].toString().toBoolean()
                        )
                    )
                }

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Desea eliminar?")
                builder.setPositiveButton(
                    "Aceptar",
                    DialogInterface.OnClickListener { dialog, which ->
                        val id = jugadores[posicionItemSeleccionado].id
                        val result = BaseDatos.BaseJugador!!.eliminarJugadorPorId(id)

                        actualizarListView()

                        if(result)
                            mostrarSnackbar("Equipo eliminado")
                        else
                            mostrarSnackbar("Error interno")

                    }
                )
                builder.setNegativeButton(
                    "Cancelar",
                    null
                )
                val dialogo = builder.create()
                dialogo.show()

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

    fun actualizarListView(){
        val position = intent.getStringExtra("id")
        if (position!=null)
        BaseDatos.BaseJugador!!.obtenerJugadores(position).addOnSuccessListener {

            val jugadores= arrayListOf <Jugador>()
            for (document in it) {
                jugadores.add(
                    Jugador(
                        document.id,
                        document.data["idequipo"].toString(),
                        document.data["nombre"].toString(),
                        document.data["nacionalidad"].toString(),
                        document.data["numero"].toString().toInt(),
                        document.data["estitular"].toString().toBoolean()
                    )
                )
            }
            val listViewJugadores = findViewById<ListView>(R.id.lv_jugadores)

            val adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                jugadores
            )
            listViewJugadores.adapter = adaptador
            adaptador.notifyDataSetChanged()
            registerForContextMenu(listViewJugadores)
        }
            .addOnFailureListener { mostrarSnackbar("fallo") }
    }
}