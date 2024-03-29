import java.util.*
import kotlin.collections.ArrayList

fun main(){
    println("Hola mundo")
    //val nombre = readLine()
    // INMUTABLES (No se reasignan "=")
    val inmutable: String = "Adrian";
    // inmutable = "Vicente";

    // Mutables (Re asignar)
    var mutable: String = "Vicente";
    mutable = "Adrian";

    // val > var
    // Duck Typing
    var ejemploVariable = " Jerson Andino "
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    //ejemploVariable = edadEjemplo;

    // Variable primitiva
    val nombreProfesor: String = "Jerson Andino"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    // Clases Java
    val fechaNacimiento: Date = Date()

    // SWITCH
    val estadoCivlWhen = "C"
    when (estadoCivlWhen){
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }
    val coqueteo = if (estadoCivlWhen == "S") "Si" else "No"

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00)
    calcularSueldo(10.00, 12.00, 20.00)
    // Parametros nombrados
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo=10.00, tasa= 14.00)

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma (null, null)

    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()

    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    // ARREGLOS

    // Tipos de Arreglos

    // Arreglo Estatico
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)

    // Arreglos Dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    // FOR EACH -> Unit
    // Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach {valorActual: Int ->
            println("Valor actual: ${valorActual}")
        }

    // it (en ingles eso) significa el elemento iterado
    arregloDinamico.forEach { println(it) }

    arregloEstatico
        .forEachIndexed { indice:Int, valorActual:Int ->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)

    // MAP -> Muta el arreglo (Cambia el arreglo)
    // 1) Enviamos el nuevo valor de la iteracion
    // 2) Nos devuelve es un NUEVO ARREGLO con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble()+100.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it+15 }
    println(respuestaMapDos)

    // FILTER -> FILTRAR EL ARREGLO
    // 1) Devolver una expresion (TRUE o FALSE)
    // 2) Nuevo arreglo filtrado

    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            //Expresion Condicion
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    // OR AND
    // OR -> ANY (Alguno cumple?)
    // AND -> ALL (Todos cumplen?)
    // V AND V -> V -- V AND F -> F
    // V OR V -> V -- V OR F -> V -- F OR F -> F

    val respuestaAny: Boolean = arregloDinamico
        .any { valorActual: Int ->
            return@any (valorActual>5)
        }
    println(respuestaAny)

    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual>5)
        }
    println(respuestaAll)

    // REDUCE -> Valor acumulado
    // Valor acumulado = 0 (Siempre 0 en lenguaje kotlin)
    // [1,2,3,4,5] -> Sumeme todos los valores del arreglo

    val respuestaReduce: Int = arregloDinamico
        .reduce { // acumulado = 0 -> SIEMPRE EMPIEZA EN 0
            acumulado: Int, valorActual: Int ->
            return@reduce (acumulado + valorActual) // Logica negocio
        }
    println(respuestaReduce)
}

fun imprimirNombre(nombre: String): Unit{
    println("Nombre: ${nombre}") // Nomenclatura para concatenar texto con variables
}
fun calcularSueldo(
    sueldo: Double, //requerido
    tasa: Double = 12.00, // Opcional (defecto)
    bonoEspecial: Double? = null, //Opcion null -> nullable
): Double{
    if(bonoEspecial == null){
        return sueldo* (100/tasa)
    }else{
        bonoEspecial.dec()
        return sueldo*(100/tasa)+bonoEspecial
    }
}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos:Int
    constructor(
        uno: Int,
        dos: Int
    ){//Bloque de codigo del constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros( // Constructor Primario
    // Ejemplo:
    // uno: Int, (parametro (sin modificador de acceso))
    // private var uno: Int, //Propiedad publica clase numeros, uno
    //  var uno: Int, // propiedadd de la clase por defecto es PUBLIC
    // public var uno: Int,
    protected val numeroUno: Int, // Propiedad de la clase protected numeros.numeroUno
    protected val numeroDos: Int, // Propiedad de la clase protected numeros.numeroDos
){
    // var cedula: string = "" (public es por defecto)
    // private valorCalculado: Int = 8 (private)
    init { //Bloque de codigo del constructor primario
        this.numeroUno; this.numeroDos; //"this" es opcional
        numeroUno; numeroDos; // sin el "this", es lo mismo
        println("Inicializando")
    }
}

class Suma( //Constructor primario suma
    uno: Int, // Parametro
    dos: Int  // Parametro
):Numeros(uno, dos){ // <- Constructor del
    init { // Bloque constructor primario
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
    }

    constructor( // Segundo constructor
        uno: Int?, // Parametros
        dos: Int   // Parametros
    ) : this( // llamada constructor primario
        if (uno == null) 0 else uno,
        dos
    )

    constructor( // tercer constructor
        uno: Int, // Parametros
        dos: Int? // Parametros
    ) : this( // llamada constructor primario
        uno,
        if (dos == null) 0 else dos
    )

    constructor( // cuarto constructor
        uno: Int?, // Parametros
        dos: Int? // Parametros
    ) : this( // llamada constructor primario
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos
    )

    public fun sumar(): Int{ // poblic por defecto o usar private
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object { // Atributos y metodos compartidos o pseudoestaticos
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int{
            return num*num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma:Int){
            historialSumas.add(valorNuevaSuma)
        }

    }
    // Si no lo necesitamos al bloque de codigo "{}" lo omitimos
}

