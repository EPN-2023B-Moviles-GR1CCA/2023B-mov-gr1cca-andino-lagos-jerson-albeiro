package com.example.examen01_mov_gr1cca_jersonandino

class BDMemoria {
    companion object {
        val equipos = arrayListOf<Equipo>()
        val jugadores = arrayListOf<Jugador>()

        /***********EQUIPOS****************/
        fun calcularIdNuevoEquipo(): Int{
            val equipo = equipos.last()
            if (equipo!=null)
                return equipo.id + 1
            else
                return 1
        }
        fun crearEquipo(
            nombre: String,
            anioCreacion: Int,
            division: Char,
            directorTecnico: String,
        ): Boolean{
            var id = calcularIdNuevoEquipo()
            return equipos.add(Equipo(id, nombre, anioCreacion, division, directorTecnico))
        }

        fun borrarEquipo(id: Int): Boolean{
            val equipo = equipos.filter { it -> it.id==id }.first()
            if(equipo != null){
                return equipos.remove(equipo)
            }
            else return false

        }
        fun editarEquipo(
            id: Int,
            nombre: String,
            anioCreacion: Int,
            division: Char,
            directorTecnico: String,
            ){
            val indice = equipos.indexOfFirst { it.id==id }
            equipos[indice] = Equipo(id,nombre,anioCreacion,division,directorTecnico)
        }
        /***********JUGADORES****************/
        fun calcularIdNuevoJugador(): Int{
            val jugador = jugadores.last()
            if( jugador!=null)
                return jugador.id + 1
            else
                return 1
        }
        fun crearJugador(
            idEquipo: Int,
            nombre: String,
            //nacimiento: Date,
            nacionalidad: String,
            numero: Int,
            esTitular: Boolean
        ): Boolean{
            var id = calcularIdNuevoJugador()
            return jugadores.add(Jugador(id, idEquipo, nombre, nacionalidad, numero, esTitular))
        }
        fun borrarJugador(id: Int): Boolean{
            val jugador=jugadores.firstOrNull { it.id==id }
            if (jugador!=null){
                return jugadores.remove(jugador)
            }
            else return false
        }
        fun editarJugador(
            id: Int,
            idEquipo: Int,
            nombre: String,
            nacionalidad: String,
            numero: Int,
            esTitular: Boolean,
        ){
            val indice = jugadores.indexOfFirst { it.id==id }
            jugadores[indice] = Jugador(id,idEquipo,nombre,nacionalidad,numero,esTitular)
        }

        init {

            equipos.add(Equipo(1,"Equipo1",2001,'A',"Director1"))
            equipos.add(Equipo(2,"Equipo2",2002,'A',"Director2"))
            equipos.add(Equipo(3,"Equipo3",2003,'A',"Director3"))
            equipos.add(Equipo(4,"Equipo4",2004,'A',"Director4"))
            equipos.add(Equipo(5,"Equipo5",2005,'A',"Director5"))
            equipos.add(Equipo(6,"Equipo6",2006,'A',"Director6"))
            equipos.add(Equipo(7,"Equipo7",2007,'A',"Director7"))
            equipos.add(Equipo(8,"Equipo8",2008,'A',"Director8"))
            equipos.add(Equipo(9,"Equipo9",2009,'A',"Director9"))
            equipos.add(Equipo(10,"Equipo10",2010,'A',"Director10"))

            jugadores.add(Jugador(1,1, "Jugador1", "EC", 1, true))
            jugadores.add(Jugador(2,1, "Jugador2", "EC", 2, true))
            jugadores.add(Jugador(3,2, "Jugador3", "EC", 3, true))
            jugadores.add(Jugador(4,2, "Jugador4", "EC", 4, true))
            jugadores.add(Jugador(5,3, "Jugador5", "EC", 5, true))
            jugadores.add(Jugador(6,3, "Jugador6", "EC", 6, true))
            jugadores.add(Jugador(7,4, "Jugador7", "EC", 7, true))
            jugadores.add(Jugador(8,4, "Jugador8", "EC", 8, true))
            jugadores.add(Jugador(9,5, "Jugador9", "EC", 9, true))
            jugadores.add(Jugador(10,5, "Jugador10", "EC", 10, true))
            jugadores.add(Jugador(11,6, "Jugador11", "EC", 11, true))
            jugadores.add(Jugador(12,6, "Jugador12", "EC", 12, true))
            jugadores.add(Jugador(13,7, "Jugador13", "EC", 13, true))
        }
    }
}