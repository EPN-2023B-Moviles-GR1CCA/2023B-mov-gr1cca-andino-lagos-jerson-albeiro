package com.example.a05_deber02_sqlite.Clases

class Jugador(
    var id: Int, // Id del jugador
    var idEquipo: Int, // Id del equipo al que pertenece
    var nombre: String, // Nombre del jugador
    //val nacimiento: Date, // fecha de nacimiento
    var nacionalidad: String, // Nacionalidad del jugador
    var numero: Int, // Numero del jugador en el equipo
    var esTitular: Boolean // Si es titular o suplente
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

        return "${id}\t${nombre}\t"
    }
}