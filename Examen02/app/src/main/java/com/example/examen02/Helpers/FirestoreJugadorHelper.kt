package com.example.examen02.Helpers

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class FirestoreJugadorHelper{
    private val TAG = "Firestore"
    private val base = Firebase.firestore


    // Zoologico

    fun crearJugador(idEquipo:String, nombre:String, nacionalidad:String, numero:Int, esTitular:Boolean ): Boolean{
        val jugador = hashMapOf(
            "idequipo" to idEquipo,
            "nombre" to nombre,
            "nacionalidad" to nacionalidad,
            "numero" to numero,
            "estitular" to esTitular
        )

        base.collection("jugadores")
            .add(jugador)
            .addOnSuccessListener { documentReference: DocumentReference ->
                Log.d(TAG, "DocumentSnapshot agregado con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e: Exception ->
                Log.w(TAG, "Error agregando documento", e)
            }
        return true
    }

    fun obtenerJugadores(id:String): Task<QuerySnapshot> {
        return base.collection("jugadores").whereEqualTo("idequipo",id).get()
    }

    fun actualizarJugador(id:String, idEquipo:String, nombre:String, nacionalidad:String, numero:Int, esTitular:Boolean): Boolean {
        val jugador = hashMapOf(
            "idequipo" to idEquipo,
            "nombre" to nombre,
            "nacionalidad" to nacionalidad,
            "numero" to numero,
            "estitular" to esTitular
        )
        Log.d("idUpdated", "${id}")

        base.collection("jugadores")
            .document(id)
            .set(jugador)

        return true
    }


    fun eliminarJugadorPorId(id: String): Boolean {
        base.collection("jugadores").document(id).delete()
        return true
    }

    fun obtenerJugadorByID(id:String):Task<DocumentSnapshot>{
        return base.collection("jugadores").document(id).get()
    }
}