package com.example.examen01_mov_gr1cca_jersonandino

class Equipo(
    val id: Int,
    val nombre: String,
    val anioCreacion: Int,
    val division: Char,
    val directorTecnico: String
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