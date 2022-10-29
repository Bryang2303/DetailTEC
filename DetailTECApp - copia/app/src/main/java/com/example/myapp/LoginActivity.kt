package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.myapp.models.ClientModel
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

// Clase de la Ventana de inicio de sesion
class LoginActivity : AppCompatActivity() {

    private var connectSql = ConnectSql()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicia base de datos
        val database = SQLiteHelper(applicationContext)

        // Boton para volver a la ventana Main
        var backLogInB = findViewById<TextView>(R.id.backLoginButton)
        backLogInB.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            //LoginActivity::class.java
        }

        // DATOS DEL INICIO DE SESION
        var loginUsername = findViewById<TextView>(R.id.usernameLoginEditText)
        var loginPassword = findViewById<TextView>(R.id.passwordLoginEditText)


        //----------------------------------------------------------------------------


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

        fun insertClientServer(Nombre_Completo:String, Cedula:String, Fecha_de_NacimientoC:String,
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

        //----------------------------------------------------------------------------




        // Boton para acceder a su usuario
        var proceedLogInB = findViewById<TextView>(R.id.loginAcceptButton)
        proceedLogInB.setOnClickListener {
            //insertClientServer("Bryan Gomez","305310094","2001-03-23",
                //"Bryang2303", "abcde", "bryang2303@gmail.com", "1000")

            if (loginPassword.text.isNotEmpty() && loginUsername.text.isNotEmpty()){
                // Acceso al usuario administrador
                if (loginPassword.text.toString()=="123" && loginUsername.text.toString()=="Root"){

                    val intent = Intent(this,RootMenuActivity::class.java)
                    startActivity(intent)
                    //loginUsername.text = "FUNCIONA"

                } else { // Comprobacion de inicio de sesion como cliente

                    // Lista de todos los clientes registrados
                    val usersList: ArrayList<ClientModel> = database.getAllClients()

                    var counter = 0
                    var clientPosition = -1
                    for (i in usersList) {
                        // Busca la cedula del cliente entre los registros para ver su posicion en usersList
                        if (loginUsername.text.toString() == usersList.get(counter).id) {
                            clientPosition = counter
                        }
                        counter++
                    }

                    if (clientPosition > -1) {
                        // Autenticacion de datos para incio de sesion exitoso
                        if (loginUsername.text.toString() == usersList.get(clientPosition).id
                            && loginPassword.text.toString() == usersList.get(clientPosition).password) {
                            val intent = Intent(this,ClientMenuActivity::class.java)
                            intent.putExtra("CLIENT_POSITION", clientPosition.toString())
                            intent.putExtra("INTENT_NAME",usersList.get(clientPosition).user)
                            startActivity(intent)
                        } else {
                            showErrorLogin()
                        }
                    } else {
                        showErrorLogin()
                    }
                    // Acceso a cualquier usuario Cliente
                }
            } else {
                // Si los datos de inicio son invalidos
                showErrorLogin()
            }
        }
    }

    // Funcion que muestra un mensaje de error en caso de haber realizado un inicio de sesion incorrectamente
    fun showErrorLogin(){
        Toast.makeText(this,"El número de identificación o la contraseña ingresados no es válido", Toast.LENGTH_SHORT).show()
    }

}


