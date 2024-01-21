package com.example.examen01_mov_gr1cca_jersonandino

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

        return "${id}\t${nombre}\t"
    }
}