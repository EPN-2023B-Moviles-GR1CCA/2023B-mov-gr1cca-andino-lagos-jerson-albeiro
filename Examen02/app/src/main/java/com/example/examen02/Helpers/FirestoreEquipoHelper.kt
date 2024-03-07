package com.example.examen02.Helpers

import android.util.Log
import com.example.examen02.Clases.Equipo
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class FirestoreEquipoHelper {
    private val TAG = "Firestore"
    private val base = Firebase.firestore


    // Zoologico

    fun crearEquipo(
        nombre: String,
        anioCreacion: Int,
        division: String,
        directorTecnico: String
    ): Boolean {
        val equipo = hashMapOf(
            "nombre" to nombre,
            "aniocreacion" to anioCreacion,
            "division" to division,
            "directortecnico" to directorTecnico
        )

        base.collection("equipos")
            .add(equipo)
            .addOnSuccessListener { documentReference: DocumentReference ->
                Log.d(TAG, "DocumentSnapshot agregado con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e: Exception ->
                Log.w(TAG, "Error agregando documento", e)
            }
        return true
    }

    fun obtenerEquipos(): Task<QuerySnapshot> {
        val lista = arrayListOf<String>()
        return base.collection("equipos")
            .get()
    }


    fun actualizarEquipo(
        id: String,
        nombre: String,
        anioCreacion: Int,
        division: String,
        directorTecnico: String
    ): Boolean {
        val equipo = hashMapOf(
            "nombre" to nombre,
            "aniocreacion" to anioCreacion,
            "division" to division,
            "directortecnico" to directorTecnico
        )
        Log.d("idUpdated", "${id}")

        base.collection("equipos")
            .document(id).set(equipo)
        return true
    }

    fun obtenerEquipoByID(id:String):Task<DocumentSnapshot>{
        return base.collection("equipos").document(id).get()
    }


    fun eliminarEquipoPorId(id: String): Boolean {
        base.collection("equipos").document(id).delete()
        return true
    }
}
