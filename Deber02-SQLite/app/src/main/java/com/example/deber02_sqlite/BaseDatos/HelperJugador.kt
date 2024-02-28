package com.example.deber02_sqlite.BaseDatos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.deber02_sqlite.Clases.Jugador

class HelperJugador(
    contexto: Context?,
) : SQLiteOpenHelper(
    contexto,
    "jugadores",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """
                CREATE TABLE JUGADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                idequipo INTEGER,
                nombre VARCHAR(50),
                nacionalidad VARCHAR(50),
                numero INTEGER,
                estitular BOOLEAN
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun crearJugador(
        idEquipo: Int, // Id del equipo al que pertenece
        nombre: String, // Nombre del jugador
        nacionalidad: String, // Nacionalidad del jugador
        numero: Int, // Numero del jugador en el equipo
        esTitular: Boolean // Si es titular o suplente
    ): Boolean{
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("idequipo",idEquipo)
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("nacionalidad",nacionalidad)
        valoresAGuardar.put("numero",numero)
        valoresAGuardar.put("estitular",esTitular)
        val resultadoGuardar = baseDatosEscritura
            .insert(
                "JUGADOR",
                null,
                valoresAGuardar
            )
        baseDatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun eliminarJugadorByID(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "JUGADOR",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarJugador(
        id: Int,
        idEquipo: Int, // Id del equipo al que pertenece
        nombre: String, // Nombre del jugador
        nacionalidad: String, // Nacionalidad del jugador
        numero: Int, // Numero del jugador en el equipo
        esTitular: Boolean // Si es titular o suplente
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("idequipo",idEquipo)
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("nacionalidad",nacionalidad)
        valoresAGuardar.put("numero",numero)
        valoresAGuardar.put("estitular",esTitular)
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

    fun consultarJugadorPorID(id:Int): Jugador {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM JUGADOR WHERE id=?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = Jugador(0,0,"","",0,false)
        //val arreglo = arrayListOf<BDEntrenador>()
        if(existeUsuario){
            do{
                val id = resultadoConsultaLectura.getInt(0)
                val idEquipo = resultadoConsultaLectura.getInt(1)
                val nombre = resultadoConsultaLectura.getString(2)
                val nacionalidad = resultadoConsultaLectura.getString(3)
                val numero = resultadoConsultaLectura.getInt(4)
                val estitular = false
                if (id != null){
                    usuarioEncontrado.id = id
                    usuarioEncontrado.idEquipo = idEquipo
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.nacionalidad = nacionalidad
                    usuarioEncontrado.numero = numero
                    usuarioEncontrado.esTitular = estitular
                    //arreglo.add(usuarioEncontrado)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }
    fun consultarJugadoresPorIDEquipo(idEquipo:Int): ArrayList<Jugador>{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM JUGADOR WHERE idequipo=?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(idEquipo.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val arreglo = arrayListOf<Jugador>()
        if(existeUsuario){
            do{
                val id = resultadoConsultaLectura.getInt(0)
                val idEquipo = resultadoConsultaLectura.getInt(1)
                val nombre = resultadoConsultaLectura.getString(2)
                val nacionalidad = resultadoConsultaLectura.getString(3)
                val numero = resultadoConsultaLectura.getInt(4)
                val estitular = false
                if (id != null){
                    arreglo.add(Jugador(id,idEquipo,nombre,nacionalidad,numero,estitular))
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arreglo
    }
}