
class Jugador(
    val id: Int, // Id del jugador
    val idEquipo: Int, // Id del equipo al que pertenece
    val nombre: String, // Nombre del jugador
    //val nacimiento: Date, // fecha de nacimiento
    val nacionalidad: String, // Nacionalidad del jugador
    val numero: Int, // Numero del jugador en el equipo
    val esTitular: Boolean // Si es titular o suplente
){
    init {
        this.id; id;
        this.idEquipo; idEquipo;
        this.nombre; nombre;
        //this.nacimiento; nacimiento;
        this.nacionalidad; nacionalidad;
        this.numero; numero;
        this.esTitular; esTitular;
    }
    override fun toString(): String {
        return "${id}.\t${nombre}"
    }

    companion object {
        val jugadores = arrayListOf<Jugador>()

        fun listarJugadoresEquipo(idEquipo: Int){
            val listaJugadores = jugadores.filter { it -> it.idEquipo==idEquipo }
            listaJugadores.forEach { it ->
                println(it)
            }
        }

        fun obtenerNumeroJugadores():Int{
            return jugadores.size
        }

        fun crearJugador(
            idEquipo: Int,
            nombre: String,
            //nacimiento: Date,
            nacionalidad: String,
            numero: Int,
            esTitular: Boolean
        ){
            var id = Jugador.obtenerNumeroJugadores()+1
            println(id)
            jugadores.add(Jugador(id, idEquipo, nombre, nacionalidad, numero, esTitular))
        }

        fun borrarJugador(id: Int){
            val jugador=Jugador.jugadores.firstOrNull { it.id==id }
            if (jugador!=null){
                Jugador.jugadores.remove(jugador)
            }
        }
    }
}