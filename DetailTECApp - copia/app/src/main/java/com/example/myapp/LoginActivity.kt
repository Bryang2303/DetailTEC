package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.myapp.models.ClientModel

// Clase de la Ventana de inicio de sesion
class LoginActivity : AppCompatActivity() {
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
        // Boton para acceder a su usuario
        var proceedLogInB = findViewById<TextView>(R.id.loginAcceptButton)
        proceedLogInB.setOnClickListener {

            if (loginPassword.text.isNotEmpty() && loginUsername.text.isNotEmpty()){
                // Acceso al usuario administrador
                if (loginPassword.text.toString()=="123" && loginUsername.text.toString()=="Root"){

                    val intent = Intent(this,RootMenuActivity::class.java)
                    startActivity(intent)
                    //loginUsername.text = "FUNCIONA"

                } else {
                    val usersList: ArrayList<ClientModel> = database.getAllClients()

                    var counter = 0
                    var clientPosition = -1
                    for (i in usersList) {
                        if (loginUsername.text.toString() == usersList.get(counter).id) {
                            clientPosition = counter
                        }
                        counter++
                    }

                    if (clientPosition > -1) {
                        if (loginUsername.text.toString() == usersList.get(clientPosition).id.toString()
                            && loginPassword.text.toString() == usersList.get(clientPosition).password.toString()) {
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


