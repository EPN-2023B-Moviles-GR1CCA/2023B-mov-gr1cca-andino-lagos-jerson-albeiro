package com.example.deber02_sqlite.BaseDatos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.deber02_sqlite.Clases.Equipo

class HelperEquipo(
    contexto: Context?,
) : SQLiteOpenHelper(
    contexto,
    "equipos",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """
                CREATE TABLE EQUIPO(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                aniocreacion INTEGER,
                division VARCHAR(1),
                directortecnico VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun crearEquipo(
        nombre: String,
        anioCreacion: Int,
        division: String,
        directorTecnico: String
    ): Boolean{
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("aniocreacion",anioCreacion)
        valoresAGuardar.put("division",division)
        valoresAGuardar.put("directortecnico",directorTecnico)
        val resultadoGuardar = baseDatosEscritura
            .insert(
                "EQUIPO",
                null,
                valoresAGuardar
            )
        baseDatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun eliminarEquipoByID(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "EQUIPO",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarEquipo(
        id: Int,
        nombre: String,
        anioCreacion: Int,
        division: String,
        directorTecnico: String
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("aniocreacion",anioCreacion)
        valoresAGuardar.put("division",division)
        valoresAGuardar.put("directortecnico",directorTecnico)
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "JUGADOR",
                valoresAGuardar,
                "id=?",
                parametrosConsultaActualizar
            )
        return if(resultadoActualizacion.toInt() == -1) false else true
    }

    fun consultarEquipoPorID(id:Int): Equipo {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM EQUIPO WHERE id=?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = Equipo(0,"",0,"","")
        //val arreglo = arrayListOf<BDEntrenador>()
        if(existeUsuario){
            do{
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val anioCreacion = resultadoConsultaLectura.getInt(2)
                val division = resultadoConsultaLectura.getString(3)
                val directorTecnico = resultadoConsultaLectura.getString(4)
                if (id != null){
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.anioCreacion = anioCreacion
                    usuarioEncontrado.division = division
                    usuarioEncontrado.directorTecnico = directorTecnico
                    //arreglo.add(usuarioEncontrado)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }
    fun consultarEquipoa(): ArrayList<Equipo>{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM EQUIPO
        """.trimIndent()
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            null
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val arreglo = arrayListOf<Equipo>()
        if(existeUsuario){
            do{
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val anioCreacion = resultadoConsultaLectura.getInt(2)
                val division = resultadoConsultaLectura.getString(3)
                val directorTecnico = resultadoConsultaLectura.getString(4)
                if (id != null){
                    arreglo.add(Equipo(id,nombre,anioCreacion,division,directorTecnico))
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arreglo
    }
}