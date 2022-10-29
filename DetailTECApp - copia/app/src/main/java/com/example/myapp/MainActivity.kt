package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import android.util.Log
import com.example.myapp.models.ClientModel
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

// Clase para la ventana inicial o Main
class MainActivity : AppCompatActivity() {
    private var connectSql = ConnectSql()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicio de la base de datos
        val database = SQLiteHelper(applicationContext)

        // Texto de bienvenida
        var HelloText = findViewById<TextView>(R.id.Hellotxt)
        // Boton para ir a la ventana de inicio de sesion

        fun selectClientsServer() {
            try {
                var count = 0

                val querry2: String = "Select count(Cedula) from CLIENTE"
                val st2: Statement? = connectSql.dbCon()?.createStatement()
                val result2: ResultSet? = st2?.executeQuery(querry2)
                if (result2 != null) {
                    result2.next()
                    count = result2.getInt(1)
                }

                val querry: String = "Select Nombre_Completo, Cedula, Fecha_de_NacimientoC, Usuario, Password, Correo, Puntos from CLIENTE"
                val st: Statement? = connectSql.dbCon()?.createStatement()
                val result: ResultSet? = st?.executeQuery(querry)

                if (result != null) {
                    result.next()

                    var selectedClientArray: ArrayList<String> = arrayListOf()


                    for (x in 1..count) {
                        for (x in 1..7) {
                            //println(result.getString(x))
                            selectedClientArray.add(result.getString(x))
                        }
                        println(selectedClientArray)
                        insertClientOnPhone(selectedClientArray)
                        selectedClientArray.clear()
                        result.next()
                    }

                }
                Toast.makeText(this,"Sincronizacion con el servidor completada",Toast.LENGTH_SHORT).show()
            } catch (ex: SQLException){
                Toast.makeText(this,"Sincronizacion con el servidor fallida", Toast.LENGTH_SHORT).show()
            }
        }

        fun selectBranchesServer() {
            try {
                var count = 0

                val querry2: String = "Select count(Nombre) from SUCURSAL"
                val st2: Statement? = connectSql.dbCon()?.createStatement()
                val result2: ResultSet? = st2?.executeQuery(querry2)
                if (result2 != null) {
                    result2.next()
                    count = result2.getInt(1)
                }

                val querry: String = "Select Nombre, Provincia, Canton, Distrito, Telefono, Fecha_de_Apertura from SUCURSAL"
                val st: Statement? = connectSql.dbCon()?.createStatement()
                val result: ResultSet? = st?.executeQuery(querry)

                if (result != null) {
                    result.next()

                    var selectedBranchArray: ArrayList<String> = arrayListOf()


                    for (x in 1..count) {
                        for (x in 1..6) {
                            //println(result.getString(x))
                            selectedBranchArray.add(result.getString(x))
                        }
                        println(selectedBranchArray)
                        selectedBranchArray.clear()
                        result.next()
                    }

                }
                Toast.makeText(this,"Sincronizacion con el servidor completada",Toast.LENGTH_SHORT).show()
            } catch (ex: SQLException){
                Toast.makeText(this,"Sincronizacion con el servidor fallida", Toast.LENGTH_SHORT).show()
            }
        }

        fun selectAppointmentsServer() {
            try {
                var count = 0

                val querry2: String = "Select count(Placa) from CITA"
                val st2: Statement? = connectSql.dbCon()?.createStatement()
                val result2: ResultSet? = st2?.executeQuery(querry2)
                if (result2 != null) {
                    result2.next()
                    count = result2.getInt(1)
                }

                val querry: String = "Select Placa, Fecha, Sucursal, Tipo, Cedula, Nombre, Puntos, Monto, IVA from CITA"
                val st: Statement? = connectSql.dbCon()?.createStatement()
                val result: ResultSet? = st?.executeQuery(querry)

                if (result != null) {
                    result.next()

                    var selectedAppointmentArray: ArrayList<String> = arrayListOf()


                    for (x in 1..count) {
                        for (x in 1..9) {
                            //println(result.getString(x))
                            selectedAppointmentArray.add(result.getString(x))
                        }
                        println(selectedAppointmentArray)
                        selectedAppointmentArray.clear()
                        result.next()
                    }

                }
                Toast.makeText(this,"Sincronizacion con el servidor completada",Toast.LENGTH_SHORT).show()
            } catch (ex: SQLException){
                Toast.makeText(this,"Sincronizacion con el servidor fallida", Toast.LENGTH_SHORT).show()
            }
        }

        fun insertClientServer(
            Nombre_Completo:String, Cedula:String, Fecha_de_NacimientoC: String?,
            Usuario:String, Password:String, Correo:String, Puntos:String) {
            ///println("EL VALOR ES $eee")
            try {
                /*
                Nombre_Completo = "Bryan Gomez"
                Cedula: String = "305310094"
                var Fecha_de_NacimientoC: String = "2001-03-23"
                var Usuario: String = "Bryang2303"
                var Password: String = "abcde"
                var Correo: String = "bryang2303@gmail.com"
                var Puntos: String = "1000"
                 */

                val usuario: PreparedStatement = connectSql.dbCon()?.prepareStatement("insert into CLIENTE values (" +
                        "'$Nombre_Completo', '$Cedula', '$Fecha_de_NacimientoC', '$Usuario', '$Password', '$Correo', $Puntos)")!!
                usuario.executeUpdate();
                Toast.makeText(this,"Registros agregados",Toast.LENGTH_SHORT).show()
            } catch (ex: SQLException){
                Log.e("Error: ", ex.message!!)
                Toast.makeText(this,"Registros no insertados", Toast.LENGTH_SHORT).show()
            }
        }

        fun uploadClientsToServer() {
            val database = SQLiteHelper(applicationContext)
            val clientList: ArrayList<ClientModel> = database.getAllClients()

            var counter = 0
            for (i in clientList) {

                var id = clientList.get(counter).id
                var name = clientList.get(counter).name
                var user = clientList.get(counter).user
                var password = clientList.get(counter).password
                var email = clientList.get(counter).email
                var points = clientList.get(counter).points.toString()

                insertClientServer(name, id, "1990-02-02", user, password, email, points)
                counter++
            }
        }


        var logInB = findViewById<TextView>(R.id.loginButton)
        logInB.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)

            selectClientsServer()
            uploadClientsToServer()
            selectClientsServer()
            //insertClientServer("Bryan Gomez","305310094","2001-03-23",
                //"Bryang2303", "abcde", "bryang2303@gmail.com", "1000")


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
    }

    private fun insertClientOnPhone(clientInformation: ArrayList<String>) {
        val database = SQLiteHelper(applicationContext)
        val name = clientInformation.get(0)
        val id = clientInformation.get(1)
        val user = clientInformation.get(3)
        val password = clientInformation.get(4)
        val email = clientInformation.get(5)
        val points = Integer.parseInt(clientInformation.get(6))

        val client = ClientModel(id = id, name = name, user = user, password = password,
            email = email, points = points)
        database.insertClient(client)
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

