package com.example.examen02

import android.app.Activity
import android.app.DownloadManager
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.examen02.Clases.Equipo
import com.example.examen02.Helpers.BaseDatos
import com.example.examen02.Helpers.FirestoreEquipoHelper
import com.example.examen02.Helpers.FirestoreJugadorHelper
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    var query: DownloadManager.Query? = null
    var arreglo: ArrayList<Equipo> = arrayListOf()

    val callbackEditarEquipo =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if (result.resultCode == Activity.RESULT_OK){
                mostrarSnackbar("Datos Guardados")
                actualizarListView()
            }
        }
    val callbackDetallesEquipo =
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
        setContentView(R.layout.activity_main)

        BaseDatos.BaseEquipo = FirestoreEquipoHelper()
        BaseDatos.BaseJugador = FirestoreJugadorHelper()

        //obtenerDatos()

        //mostrarSnackbar(arreglo.size.toString())
    }

    override fun onStart() {
        super.onStart()
        BaseDatos.BaseEquipo!!.obtenerEquipos().addOnSuccessListener {
            val equipos= arrayListOf <Equipo>()
            for (document in it) {
                equipos.add(
                    Equipo(
                        document.id,
                        document.data["nombre"].toString(),
                        document.data["aniocreacion"].toString().toInt(),
                        document.data["division"].toString(),
                        document.data["directortecnico"].toString()
                    )
                )
            }
            val listViewEquipos = findViewById<ListView>(R.id.lv_equipos)
            val adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                equipos
            )
            listViewEquipos.adapter = adaptador
            adaptador.notifyDataSetChanged()

            registerForContextMenu(listViewEquipos)
        }
            .addOnFailureListener { mostrarSnackbar("fallo") }

        val btn_crear_equipo = findViewById<Button>(R.id.btn_nuevo_equipo)
        btn_crear_equipo
            .setOnClickListener {
                lanzarIntentEditarEquipo("CREAR EQUIPO",null)
            }

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
                BaseDatos.BaseEquipo!!.obtenerEquipos()
                    .addOnSuccessListener {
                        val equipos= arrayListOf <Equipo>()
                        for (document in it) {
                            equipos.add(
                                Equipo(
                                    document.id,
                                    document.data["nombre"].toString(),
                                    document.data["aniocreacion"].toString().toInt(),
                                    document.data["division"].toString(),
                                    document.data["directortecnico"].toString()
                                )
                            )
                        }
                        val id = equipos[posicionItemSeleccionado].id
                        lanzarIntentEditarEquipo("EDITAR EQUIPO",id)
                    }
                return true
            }
            R.id.mi_equipo_eliminar -> {
                abrirDialogo()
                return true
            }
            R.id.mi_equipo_detalles -> {
                BaseDatos.BaseEquipo!!.obtenerEquipos()
                    .addOnSuccessListener {
                        val equipos= arrayListOf <Equipo>()
                        for (document in it) {
                            equipos.add(
                                Equipo(
                                    document.id,
                                    document.data["nombre"].toString(),
                                    document.data["aniocreacion"].toString().toInt(),
                                    document.data["division"].toString(),
                                    document.data["directortecnico"].toString()
                                )
                            )
                        }
                        val id = equipos[posicionItemSeleccionado].id
                        lanzarIntentDetallesEquipo(id)
                    }
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun obtenerDatos(){
        val db = Firebase.firestore
        var collection = db.collection("equipos")
        var size = 0
        collection.get().addOnSuccessListener {
            mostrarSnackbar(it.size().toString())
        }
            .addOnFailureListener { mostrarSnackbar("algo fallo") }

    }
    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.lv_equipos),
                texto,
                Snackbar.LENGTH_LONG,
            )
            .show()
    }
    fun actualizarListView(){
        BaseDatos.BaseEquipo!!.obtenerEquipos().addOnSuccessListener {
            val equipos= arrayListOf <Equipo>()
            for (document in it) {
                equipos.add(
                    Equipo(
                        document.id,
                        document.data["nombre"].toString(),
                        document.data["aniocreacion"].toString().toInt(),
                        document.data["division"].toString(),
                        document.data["directortecnico"].toString()
                    )
                )
            }
            arreglo = equipos
            val listViewEquipos = findViewById<ListView>(R.id.lv_equipos)

            val adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                arreglo
            )
            listViewEquipos.adapter = adaptador
            adaptador.notifyDataSetChanged()
            registerForContextMenu(listViewEquipos)
        }
            .addOnFailureListener { mostrarSnackbar("fallo") }
    }
    fun lanzarIntentEditarEquipo(titulo:String, id:String?){
        val intentEditarEquipo = Intent(this, FormularioEquipo::class.java)
        intentEditarEquipo.putExtra("titulo",titulo)
        intentEditarEquipo.putExtra("id",id)
        callbackEditarEquipo.launch(intentEditarEquipo)
    }

    fun lanzarIntentDetallesEquipo(id: String){
        val intentEditarEquipo = Intent(this, InfoEquipo::class.java)
        intentEditarEquipo.putExtra("id",id)
        callbackDetallesEquipo.launch(intentEditarEquipo)
    }

    fun abrirDialogo(){
        BaseDatos.BaseEquipo!!.obtenerEquipos()
            .addOnSuccessListener {
                val equipos= arrayListOf <Equipo>()
                for (document in it) {
                    equipos.add(
                        Equipo(
                            document.id,
                            document.data["nombre"].toString(),
                            document.data["aniocreacion"].toString().toInt(),
                            document.data["division"].toString(),
                            document.data["directortecnico"].toString()
                        )
                    )
                }

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Desea eliminar?")
                builder.setPositiveButton(
                    "Aceptar",
                    DialogInterface.OnClickListener { dialog, which ->
                        val id = equipos[posicionItemSeleccionado].id
                        val result = BaseDatos.BaseEquipo!!.eliminarEquipoPorId(id)

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
}