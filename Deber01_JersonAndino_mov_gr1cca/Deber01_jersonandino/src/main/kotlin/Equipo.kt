class Equipo{
    val id: Int
    val nombre: String
    val anioCreacion: Int
    val division: Char
    val directorTecnico: String
    val jugadores: ArrayList<Jugador>

    constructor(
        id: Int,
        nombre: String,
        anioCreacion: Int,
        division: Char,
        directorTecnico: String,
    ){
        this.id = id
        this.nombre = nombre
        this.anioCreacion = anioCreacion
        this.division = division
        this.directorTecnico = directorTecnico
        this.jugadores= arrayListOf<Jugador>()
    }
    override fun toString(): String {
        return "${id}.\t${nombre}"
    }

    companion object{
        val equipos = arrayListOf<Equipo>()

        fun listarEquipos(){
            equipos.forEach { it -> println(it) }
        }

        fun obtenerNumeroEquipos():Int{
            return equipos.size
        }

        fun crearEquipo(
            nombre: String,
            anioCreacion: Int,
            division: Char,
            directorTecnico: String,
        ){
            var id = Equipo.obtenerNumeroEquipos()+1
            println(id)
            equipos.add(Equipo(id, nombre, anioCreacion, division, directorTecnico))
        }

        fun borrarEquipo(id: Int){
            val equipo = Equipo.equipos.filter { it -> it.id==id }[0]
            Equipo.equipos.remove(equipo)
        }
    }
}
