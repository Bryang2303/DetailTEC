package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
// Clase de la Ventana de inicio de sesion
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
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
                    // Acceso a cualquier usuario Cliente
                    val intent = Intent(this,ClientMenuActivity::class.java)
                    intent.putExtra("INTENT_NAME",loginUsername.text)
                    startActivity(intent)


                }

            } else {
                // Si los datos de inicio son invalidos
                showErrorLogin()
            }
        }

    }

    // Funcion que muestra un mensaje de error en caso de haber realizado un inicio de sesion incorrectamente
    fun showErrorLogin(){
        Toast.makeText(this,"El nombre de usuario o la contraseña ingresados no es válido", Toast.LENGTH_SHORT).show()
    }

}


