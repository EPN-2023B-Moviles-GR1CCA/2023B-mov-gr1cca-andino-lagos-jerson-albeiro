package com.example.gr1accjaal2023b

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result ->
            if (result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    //Logica Negocio
                    val data = result.data
                    mostrarSnackbar(
                        "${data?.getStringExtra("nombreModificado")}"
                    )
                }
            }
        }
    fun mostrarSnackbar(texto:String){
        Snackbar.make(
            findViewById(R.id.id_layout_main),
            texto,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    val callbackIntentImplicitoTelefono =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    if(result.data!!.data != null){
                        var uri: Uri = result.data!!.data!!
                        val cursor = contentResolver.query(
                            uri, null, null, null, null, null
                        )
                        cursor?.moveToFirst()
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono = cursor?.getString(indiceTelefono!!)
                        cursor?.close()
                        mostrarSnackbar("Telefono: ${telefono}")
                    }
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EBaseDeDatos.tablaEntrenador = ESqliteHelperEntrenador(this)

        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonCicloVida
            .setOnClickListener{
                irActividad(ACicloVida::class.java)
            }
        val botonListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonListView
            .setOnClickListener{
                irActividad(BListView::class.java)
            }
        val botonIntentImplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonIntentImplicito
            .setOnClickListener {
                val intentConRespuesta = Intent(
                    Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                )
                callbackIntentImplicitoTelefono.launch(intentConRespuesta)
            }
        val botonIntentExplicito = findViewById<Button>(R.id.btn_ir_intent_explicito)
        botonIntentExplicito
            .setOnClickListener {
                abrirActividadConParametros(CIntentExplicitoParametros::class.java)
            }

        val botonSQLite = findViewById<Button>(R.id.btn_sqlite)
        botonSQLite
            .setOnClickListener {
                irActividad(ECrudEntrenador::class.java)
            }

        val botonFReciclerView = findViewById<Button>(R.id.btn_recicler_view)
        botonFReciclerView.setOnClickListener {
            irActividad(FReciclerView::class.java)
        }

        val botonMaps = findViewById<Button>(R.id.btn_gmaps)
        botonMaps.setOnClickListener {
            irActividad(GGoogleMapsActivity::class.java)
        }

        val botonFAuth = findViewById<Button>(R.id.btn_intent_firebase)
        botonFAuth.setOnClickListener {
            irActividad(HFirebaseUIAuth::class.java)
        }
    }
    fun abrirActividadConParametros(clase: Class<*>){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombre","Jerson")
        intentExplicito.putExtra("apellido","andino")
        intentExplicito.putExtra("edad", 23)
        callbackContenidoIntentExplicito.launch(intentExplicito)
    }
    fun irActividad(
        clase: Class<*>
    ){
        var intent = Intent(this, clase)
        startActivity(intent)
    }
}
