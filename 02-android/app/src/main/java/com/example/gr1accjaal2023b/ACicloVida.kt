package com.example.gr1accjaal2023b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {
    var textoGlobal = ""
    fun mostrarSnackbar(texto:String){
        textoGlobal = textoGlobal + "" + texto
        Snackbar
            .make(
                findViewById(R.id.cl_ciclo_vida),
                textoGlobal,
                Snackbar.LENGTH_INDEFINITE
            )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackbar("OnCreate")
    }

    override fun onStart(){
        super.onStart()
        mostrarSnackbar("OnStart")
    }

    override fun onResume() {
        super.onResume()
        mostrarSnackbar("OnResume")
    }

    override fun onRestart() {
        super.onRestart()
        mostrarSnackbar("OnRestart")
    }

    override fun onPause() {
        super.onPause()
        mostrarSnackbar("OnPause")
    }

    override fun onStop() {
        super.onStop()
        mostrarSnackbar("OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarSnackbar("OnDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            // RECUPERAR LAS VARIABLES
            // PRIMITIVOS
            putString("texto guardado",textoGlobal)
            //putInt("numeroGuardado", numero)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle,
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        //RECUPERAR LAS VARIABLES
        //PRIMITIVOS
        val textoRecuperado:String? = savedInstanceState
            .getString("textoGuardado")
        // val textoRecuperado:Int= = savedInstandeState.getInt("xx")
        if(textoRecuperado!=null){
            mostrarSnackbar(textoRecuperado)
            textoGlobal=textoRecuperado
        }
    }
}