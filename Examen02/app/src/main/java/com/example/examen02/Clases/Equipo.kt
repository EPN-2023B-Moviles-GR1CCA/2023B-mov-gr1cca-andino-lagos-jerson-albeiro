package com.example.examen02.Clases

class Equipo(
    var id: String,
    var nombre: String,
    var anioCreacion: Int,
    var division: String,
    var directorTecnico: String
) {
    init {
        this.id; id;
        this.nombre; nombre;
        this.anioCreacion; anioCreacion;
        this.division; division;
        this.directorTecnico; directorTecnico;
    }

    override fun toString(): String {
        return "${id}\t${nombre}"
    }
}