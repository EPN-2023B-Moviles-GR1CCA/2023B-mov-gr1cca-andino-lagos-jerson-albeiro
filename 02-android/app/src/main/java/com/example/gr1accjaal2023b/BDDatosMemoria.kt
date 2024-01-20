package com.example.gr1accjaal2023b

class BDDatosMemoria {
    companion object {
        val arregloBEntrenador = arrayListOf<BDEntrenador>()

        init {
            arregloBEntrenador
                .add(
                    BDEntrenador(1,"Adrian","a@a.com")
                )
            arregloBEntrenador
                .add(
                    BDEntrenador(2,"Vicente","b@b.com")
                )
            arregloBEntrenador
                .add(
                    BDEntrenador(3,"Carolina","c@c.com")
                )
        }
    }
}