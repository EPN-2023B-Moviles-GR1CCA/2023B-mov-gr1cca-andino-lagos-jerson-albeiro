package com.example.examen01_mov_gr1cca_jersonandino

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
import com.google.android.material.snackbar.Snackbar

class InfoEquipo : AppCompatActivity() {

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

        val position = intent.getIntExtra("position",-1)
        val equipo=BDMemoria.equipos[position]

        val tvNombre = findViewById<TextView>(R.id.tv_nombre_equipo)
        val tvAnio = findViewById<TextView>(R.id.tv_anio_equipo)
        val tvDivision = findViewById<TextView>(R.id.tv_division_equipo)
        val tvDirector = findViewById<TextView>(R.id.tv_director_equipo)

        if(equipo != null){
            tvNombre.text = "Nombre: ${equipo.nombre.toString()}"
            tvDirector.text = "Director: ${equipo.directorTecnico.toString()}"
            tvAnio.text = "Año: ${equipo.anioCreacion.toString()}"
            tvDivision.text = "Division: ${equipo.division.toString()}"
        }

        val lv_jugadores = findViewById<ListView>(R.id.lv_jugadores)
        actualizarListView()

        val btnNuevoJugador = findViewById<Button>(R.id.btn_nuevo_jugador)
        btnNuevoJugador
            .setOnClickListener {
                lanzarIntentEditarJugador("CREAR JUGADOR", -1,equipo.id)
            }

        registerForContextMenu(lv_jugadores)

    }

    var posicionItemSeleccionado = 0
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_equipo ,menu)
        //Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_equipo_editar -> {
                val position = intent.getIntExtra("position",-1)
                val id=BDMemoria.equipos[position].id
                val jugadores = BDMemoria.jugadores.filter { it -> it.idEquipo == id }
                val id_jugador = jugadores[posicionItemSeleccionado].id
                lanzarIntentEditarJugador("EDITAR JUGADOR",id_jugador,id)
                return true
            }
            R.id.mi_equipo_eliminar -> {
                abrirDialogo()
                return true
            }
            R.id.mi_equipo_detalles -> {
                val position = intent.getIntExtra("position",-1)
                val id=BDMemoria.equipos[position].id
                val jugadores = BDMemoria.jugadores.filter { it -> it.idEquipo == id }
                val id_jugador = jugadores[posicionItemSeleccionado].id
                lanzarIntentDetallesJugador(id_jugador)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun lanzarIntentEditarJugador(titulo:String, id:Int, idEquipo:Int){
        val intentEditarJugador = Intent(this, FormularioJugador::class.java)
        intentEditarJugador.putExtra("titulo",titulo)
        intentEditarJugador.putExtra("idJugador",id)
        intentEditarJugador.putExtra("idEquipo",idEquipo)
        callbackEditarJugador.launch(intentEditarJugador)
    }
    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->
                val position = intent.getIntExtra("position",-1)
                val id=BDMemoria.equipos[position].id
                var jugadores = BDMemoria.jugadores.filter { it -> it.idEquipo == id }
                val id_jugador = jugadores[posicionItemSeleccionado].id
                val result = BDMemoria.borrarJugador(id_jugador)

                actualizarListView()

                if(result)
                    mostrarSnackbar("Jugador eliminado")
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

    fun lanzarIntentDetallesJugador(id:Int){
        val intentEditarJugador = Intent(this, InfoJugador::class.java)
        intentEditarJugador.putExtra("id",id)
        callbackDetallesJugador.launch(intentEditarJugador)
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
        val position = intent.getIntExtra("position",-1)
        val id=BDMemoria.equipos[position].id
        val lv_jugadores = findViewById<ListView>(R.id.lv_jugadores)
        val jugadores = BDMemoria.jugadores.filter { it -> it.idEquipo == id }
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // como se va a ver (XML)
            jugadores
        )
        lv_jugadores.adapter = adaptador

        adaptador.notifyDataSetChanged()
    }
}