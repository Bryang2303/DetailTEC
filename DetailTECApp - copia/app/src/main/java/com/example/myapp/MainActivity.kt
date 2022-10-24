package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.app.ActionBar
// Clase para la ventana inicial o Main
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicio de la base de datos
        val database = SQLiteHelper(applicationContext)
//        database.deleteClientPhone(208140292, "66666666")
//        database.deleteClientPhone(208140292, "62192009")
//        database.deleteClientAddress(208140292)

        // Texto de bienvenida
        var HelloText = findViewById<TextView>(R.id.Hellotxt)
        // Boton para ir a la ventana de inicio de sesion
        var logInB = findViewById<TextView>(R.id.loginButton)
        logInB.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
//            intent.putExtra("database", database)
            startActivity(intent)
            //LoginActivity::class.java
        }
        // Boton para ir a la ventana de Registro
        var singInB = findViewById<TextView>(R.id.singinButton)
        singInB.setOnClickListener {
            val intent = Intent(this,SinginActivity::class.java)
//            intent.putExtra("database", database)
            startActivity(intent)
            //LoginActivity::class.java
        }

        //HelloText.text = "BYE WORLD"

        //variablesConstantes()
        //funciones("ABC",1)
        //clases()
    }
    // Funcion para mostrar un error en donde el nombre de usuario se encuentra vacio
    fun showErrorName(){
        Toast.makeText(this,"El nombre de usuario no puede ser vacio",Toast.LENGTH_SHORT).show()
    }
























    // FUNCION UNICAMENTE DE AYUDA
    fun funciones(nom: String,nu: Int) :Int{
        println(nom)
        return(nu*2)
    }
    // FUNCION UNICAMENTE DE AYUDA
    fun clases(){
        var me = Programmer("Bryan",21, arrayOf("Py","Jav","C"), arrayOf(Programmer.SO.IOS,Programmer.SO.ANDROID))
        println(me.name)
        me.age+=1
        me.code()
        println("Me gusta ${me.SOs[1]}")
    }
   // FUNCION UNICAMENTE DE AYUDA
    private fun variablesConstantes() {
        //Variables
        var myFirstVariable = "Hello W"
        println(myFirstVariable)
        myFirstVariable = "Welcome"
        println(myFirstVariable)
        // val es para constantes
        var Num = 5
        val myDouble: Double = 3.14
        // IF
        if (Num < 10){
            println("$Num es menor que 10") //$ es para obtener el valor como string
        } else {
            println("$Num es mayor que 10")
        }
        // WHEN
        val case = "A"
        when(case){
            "A" -> {
                println("Es A")
            }
            "B" -> {
                println("Es B")
            } else  -> {
                println("No es A ni B")
            }
        }
        var case2 = 5
        when(case2){
            1,2,3 -> {
                println("Es 1,2 o 3")
            }
            in 3..10 -> {
                println("Esta en el rango de 3 a 10")
            } else  -> {
            println("No es valido")
            }
        }
        //ARRAYS
        var name = "Bryan"
        var age = "21"
        var career = "CE"
        var myArray: ArrayList<String> = arrayListOf()
        myArray.add(name)
        myArray.add(age)
        myArray.add(career)
        myArray.addAll(listOf("A","B"))
        println(myArray)
        myArray[1] = "20"
        println(myArray)
        myArray.removeAt(4)
        myArray.forEach { println(it) } // it referencia a cada elemento
        myArray.count() // len
        //myArray.clear()    limpiar el array
        //MAPS
        var myMap:MutableMap<String, Int>
        myMap = mutableMapOf("Juan" to 1, "Pedro" to 2, "Miguel" to 3)
        println(myMap)
        myMap["Ana"] = 7 // Meter valores al map
        myMap.put("Maria", 8)
        println(myMap)
        println(myMap["Ana"])
        myMap.remove("Maria")
        println(myMap)
        //LOOPS
        //For
        for (myString in myArray){
            println(myString)
        }
        for (myElement in myMap){
            println("${myElement.key}-${myElement.value}")
        }
        for (x in 0..10){
            println(x)
        }
        for (x in 0 until 10){ // No toma en cuentra el 10
            println(x)
        }
        for (x in 0..10 step 2){ // De dos en dos
            println(x)
        }
        for (x in 0 downTo  10 step 3){ // De 3 en 3 pero para atras
            println(x)
        }
        //While
        var y = 0
        while (y<10){
            println(y)
            y++
        }
    }
}

