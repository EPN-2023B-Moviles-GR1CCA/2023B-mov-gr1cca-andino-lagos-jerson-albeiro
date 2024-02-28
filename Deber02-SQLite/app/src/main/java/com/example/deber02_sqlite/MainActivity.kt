package com.example.deber02_sqlite

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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.deber02_sqlite.BaseDatos.BaseDeDatos
import com.example.deber02_sqlite.BaseDatos.HelperEquipo
import com.example.deber02_sqlite.BaseDatos.HelperJugador
import com.example.deber02_sqlite.Clases.Equipo
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    var equipos = arrayListOf<Equipo>()

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

        BaseDeDatos.tablaEquipo = HelperEquipo(this)
        BaseDeDatos.tablaJugador = HelperJugador(this)

        equipos = BaseDeDatos.tablaEquipo!!.consultarEquipoa()

        val lv_equipos = findViewById<ListView>(R.id.lv_equipos)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // como se va a ver (XML)
            equipos
        )
        lv_equipos.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val btn_crear_equipo = findViewById<Button>(R.id.btn_nuevo_equipo)
        btn_crear_equipo
            .setOnClickListener {
                lanzarIntentEditarEquipo("CREAR EQUIPO",0)
            }
        registerForContextMenu(lv_equipos)
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
                val id = equipos[posicionItemSeleccionado].id
                lanzarIntentEditarEquipo("EDITAR EQUIPO",id)
                return true
            }
            R.id.mi_equipo_eliminar -> {
                abrirDialogo()
                return true
            }
            R.id.mi_equipo_detalles -> {
                val id = equipos[posicionItemSeleccionado].id
                lanzarIntentDetallesEquipo(id)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    fun lanzarIntentEditarEquipo(titulo:String, position:Int){
        val intentEditarEquipo = Intent(this, FormularioEquipo::class.java)
        intentEditarEquipo.putExtra("titulo",titulo)
        intentEditarEquipo.putExtra("position",position)
        callbackEditarEquipo.launch(intentEditarEquipo)
    }

    fun lanzarIntentDetallesEquipo(position: Int){
        val intentEditarEquipo = Intent(this, InfoEquipo::class.java)
        intentEditarEquipo.putExtra("position",position)
        callbackDetallesEquipo.launch(intentEditarEquipo)
    }
    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->
                val id = equipos[posicionItemSeleccionado].id
                val result = BaseDeDatos.tablaEquipo!!.eliminarEquipoByID(id)

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
    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.lv_equipos),
                texto,
                Snackbar.LENGTH_LONG,
            )
            .show()
    }
    fun irActividad(
        clase: Class<*>
    ){
        var intent = Intent(this, clase)
        startActivity(intent)
    }

    fun actualizarListView(){
        equipos = BaseDeDatos.tablaEquipo!!.consultarEquipoa()
        val lv_equipos = findViewById<ListView>(R.id.lv_equipos)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // como se va a ver (XML)
            equipos
        )
        lv_equipos.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }
}