package com.example.examen01_mov_gr1cca_jersonandino

class Equipo(
    var id: Int,
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