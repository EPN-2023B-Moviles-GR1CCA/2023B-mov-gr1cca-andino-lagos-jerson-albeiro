package com.example.gr1accjaal2023b

class BDEntrenador (
    var id:Int,
    var nombre:String?,
    var descripcion:String?
){
    override fun toString(): String {
        return "$nombre - $descripcion"
    }

}