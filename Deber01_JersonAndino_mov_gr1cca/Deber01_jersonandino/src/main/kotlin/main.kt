import java.util.Date
import java.io.File
import java.io.InputStream

fun main(){

    leerDatosEquipos()
    leerDatosJugadores()

    var respUno=""
    while (respUno!="5"){
        mostrarMenuInicial()
        respUno = readLine().toString()
        when (respUno){
            "1" -> {
                listarEquipos()
                println("\t--SELECCIONA UN EQUIPO PARA ACCEDER A SU INFORMACION")
                var respDos = readLine()
                if (respDos != null && respDos!=""){
                    var respDosInt = respDos.toInt()
                    if (Equipo.equipos.any { it -> it.id==respDosInt }){
                        mostrarInformacionEquipo(respDosInt)
                        mostrarMenuEquipo()
                        val respTres = readLine()
                        when (respTres){
                            "1" -> {
                                Equipo.borrarEquipo(respDosInt)
                            }
                            "2" -> {
                                editarEquipo(respDosInt)
                            }
                            "3" -> {
                                crearJugador(respDosInt)
                            }
                            "4" -> {
                                listarJugadores(respDosInt)

                                println("\t--SELECCIONA UN JUGADOR PARA ACCEDER A SU INFORMACION")
                                var respCuatro = readLine()
                                if (respCuatro != null && respCuatro!="") {
                                    var respCuatroInt = respCuatro.toInt()
                                    if (Jugador.jugadores.any{ it -> it.id == respCuatroInt}) {
                                        mostrarInformacionJugador(respCuatroInt)
                                        mostrarMenuJugador()
                                        val respCinco = readLine()
                                        when (respCinco) {
                                            "1" -> {
                                                Jugador.borrarJugador(respCuatroInt)
                                            }
                                            "2" -> {
                                                editarJugador(respCuatroInt)
                                            }

                                            "3" -> {
                                            }
                                        }
                                    } else {
                                        println("EL JUGADOR SELECCIONADO NO EXISTE SELECCIONADO NO EXISTE")
                                    }
                                }
                            }
                            "5" -> {

                            }
                        }
                    }else{
                        println("EL EQUIPO SELECCIONADO NO EXISTE")
                    }
                }else{

                }
            }
            "2" -> {
                crearEquipo()
            }
            "3" -> {

            }
            else -> {
                println("ESCOJA UNA OPCION CORRECTA")
            }
        }
        escribirDatosEquipos()
        escribirDatosJugadores()
    }
}

fun leerDatosEquipos(){
    // LEER ARCHIVO
    val pathName = "Deber01_jersonandino/src/equipos.txt"
    val miArchivo = File(pathName)

    //val lineas = miArchivo.readLines()
    //lineas.forEach { println(it) } // muestra todo el archivo

    val lineasLista = mutableListOf<String>()
    var contador = 0
    miArchivo.useLines { lines -> lines.forEach { lineasLista.add(it) } }
    lineasLista.forEach {
        var registro = it.split(',').toTypedArray()
        var id = registro[1].toInt()
        var nombre = registro[2].toString()
        var anioCreacion = registro[3].toInt()
        var division = registro[4].toCharArray()[0]
        var directorTecnico = registro[5].toString()
        Equipo.equipos.add(Equipo(id,nombre,anioCreacion,division,directorTecnico))
        contador++
    } // muestra todo el archivo
    println("${contador} equipos cargados")
}

fun leerDatosJugadores(){
    // LEER ARCHIVO
    val pathName = "Deber01_jersonandino/src/jugadores.txt"
    val miArchivo = File(pathName)

    val lineasLista = mutableListOf<String>()
    var contador = 0
    miArchivo.useLines { lines -> lines.forEach { lineasLista.add(it) } }
    lineasLista.forEach {
        var registro = it.split(',').toTypedArray()
        var id = registro[1].toInt()
        var idEquipo = registro[2].toInt()
        var nombre = registro[3].toString()
        var nacionalidad = registro[4].toString()
        var numero = registro[5].toInt()
        var esTitular = registro[6].toBoolean()
        Jugador.jugadores.add(Jugador(id, idEquipo, nombre, nacionalidad, numero, esTitular))
        contador++
    } // muestra todo el archivo
    println("${contador} jugadores cargados")
}

fun escribirDatosEquipos(){
    val ruta = "Deber01_jersonandino/src/equipos.txt"
    val archivo = File(ruta)

    archivo.printWriter().use { out ->
        Equipo.equipos.forEach { it ->
            out.println("equipo,${it.id},${it.nombre},${it.anioCreacion},${it.division},${it.directorTecnico}")
        }
    }
}

fun escribirDatosJugadores(){
    val ruta = "Deber01_jersonandino/src/jugadores.txt"
    val archivo = File(ruta)

    archivo.printWriter().use { out ->
        Jugador.jugadores.forEach { it ->
            out.println("jugador,${it.id},${it.idEquipo},${it.nombre},${it.nacionalidad},${it.numero},${it.esTitular}")
        }
    }
}

fun mostrarMenuInicial(){
    println("########################################################")
    println("GESTION DE EQUIPOS")
    println("1.\tLISTAR EQUIPOS")
    println("2.\tCREAR EQUIPO")
    println("5.\tSALIR")
    println("########################################################")
}

fun mostrarMenuEquipo(){
    println("########################################################")
    println("1.\tBORRAR EQUIPO")
    println("2.\tEDITAR EQUIPO")
    println("3.\tCREAR JUGADOR")
    println("4.\tLISTAR JUGADORES")
    println("5.\tSALIR")
    println("########################################################")
}
fun mostrarMenuJugador(){
    println("########################################################")
    println("1.\tBORRAR JUGADOR")
    println("2.\tEDITAR JUGADOR")
    println("3.\tSALIR")
    println("########################################################")
}

fun listarEquipos(){
    Equipo.equipos.forEach { it ->
        println("${it.id} - ${it.nombre}")
    }
}

fun mostrarInformacionEquipo(id: Int){
    val equipo = Equipo.equipos.filter { it ->
        it.id==id
    }[0]
    println("NOMBRE:\t${equipo.nombre}")
    println("AÑO CREACION::\t${equipo.anioCreacion}")
    println("DIVISION:\t${equipo.division}")
    println("D. T.: :\t${equipo.directorTecnico}")
}

fun editarEquipo(id: Int){
    val equipo = Equipo.equipos.filter { it ->
        it.id==id
    }[0]
    val indice = Equipo.equipos.indexOfFirst { it.id==id }
    print("NOMBRE (${equipo.nombre}):\t")
    val nombre = readLine().toString()
    print("AÑO CREACION (${equipo.anioCreacion}):\t")
    val anio = readLine()
    if (anio!=null){
        val anioCreacion = anio.toInt()
        print("DIVISION (${equipo.division}):\t")
        val char = readLine().toString()
        val division = char.toCharArray()[0]
        print("D. T. (${equipo.directorTecnico}):\t")
        val directorTecnico = readLine().toString()

        Equipo.equipos[indice] = Equipo(id,nombre,anioCreacion,division,directorTecnico)
    }
}

fun crearEquipo(){
    print("NOMBRE:\t")
    val nombre = readLine().toString()
    print("AÑO CREACION:\t")
    val anio = readLine()
    if (anio!=null){
        val anioCreacion = anio.toInt()
        print("DIVISION:\t")
        val char = readLine().toString()
        val division = char.toCharArray()[0]
        print("D. T.:\t")
        val directorTecnico = readLine().toString()

        Equipo.crearEquipo(nombre, anioCreacion, division, directorTecnico)
    }
}

fun listarJugadores(idEquipo:Int){
    val jugadores=Jugador.jugadores.filter { it.idEquipo==idEquipo }
    jugadores.forEach { it->
        println("${it.id} - ${it.nombre}")
    }
}

fun mostrarInformacionJugador(id:Int){
    val jugador = Jugador.jugadores.firstOrNull { it.id==id }
    if (jugador!=null){
        println("NOMBRE:\t${jugador.nombre}")
        println("NACIONALIDAD::\t${jugador.nacionalidad}")
        println("NUMERO:\t${jugador.numero}")
        println("TITULAR:\t${jugador.esTitular}")
    }
}

fun editarJugador(id: Int){
    val jugador = Jugador.jugadores.firstOrNull { it.id == id }
    val indice = Jugador.jugadores.indexOfFirst { it.id == id }
    if (jugador!=null){
        val idEquipo = jugador.idEquipo
        print("NOMBRE (${jugador.nombre}):\t")
        val nombre = readLine().toString()
        print("NACIONALIDAD (${jugador.nacionalidad}):\t")
        val nacionalidad = readLine().toString()
        print("NUMERO (${jugador.numero})")
        val numero = readLine()
        if (numero!=null){
            val numeroInt = numero.toInt()
            print("ES TITULAR? (s/n) (${jugador.esTitular}):\t")
            val esTitular = readLine().toString()
            val esTitularBoolean = false
            if (esTitular=="s" || esTitular=="S"){
                val esTitularBoolean = true
            }
            Jugador.jugadores[indice] = Jugador(id,idEquipo,nombre,nacionalidad,numeroInt,esTitularBoolean)
        }
    }
}
fun crearJugador(idEquipo: Int){
    print("NOMBRE:\t")
    val nombre = readLine().toString()
    print("NACIONALIDAD:\t")
    val nacionalidad = readLine().toString()
    print("NUMERO")
    val numero = readLine()
    if (numero!=null){
        val numeroInt = numero.toInt()
        print("ES TITULAR? (s/n):\t")
        val esTitular = readLine().toString()
        val esTitularBoolean = false
        if (esTitular=="s" || esTitular=="S"){
            val esTitularBoolean = true
        }

        Jugador.crearJugador(idEquipo,nombre,nacionalidad,numeroInt,esTitularBoolean)
    }
}
