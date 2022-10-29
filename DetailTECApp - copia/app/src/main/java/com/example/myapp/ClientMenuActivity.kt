package com.example.myapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.widget.TextView
import com.example.myapp.models.ClientModel

// Clase de la ventana del menu para el cliente
class ClientMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_menu)

        // Iniciar base de datos
        val database = SQLiteHelper(applicationContext)

        // Pasar el nombre de usuario de manera de que el cliente se mantenga logeado
        val bundle = intent.extras
        val name = bundle?.get("INTENT_NAME")
        var clientPosition = bundle?.get("CLIENT_POSITION")
        var usernameClientMenu = findViewById<TextView>(R.id.clientNameTextViewMenu)
        usernameClientMenu.text = name.toString()


        // Boton para volver a la ventana de inicio de sesion
        var backClientMenuB = findViewById<TextView>(R.id.singoffClientButton)
        backClientMenuB.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            //LoginActivity::class.java
        }
        // Boton Abrir la ventana de los datos del cliente
        var clientDataB = findViewById<TextView>(R.id.myDataButton)
        clientDataB.setOnClickListener {
            val intent = Intent(this,ClientDataActivity::class.java)
            intent.putExtra("INTENT_NAME",usernameClientMenu.text)
            intent.putExtra("CLIENT_POSITION", clientPosition.toString())
            startActivity(intent)
            //LoginActivity::class.java
        }
        // Boton Abrir la ventana de solicitud de cita del cliente
        var clientAppointmentB = findViewById<TextView>(R.id.appointmentClientButton)
        clientAppointmentB.setOnClickListener {
            val intent = Intent(this,ClientAppointmentActivity::class.java)
            intent.putExtra("INTENT_NAME",usernameClientMenu.text)
            intent.putExtra("CLIENT_POSITION", clientPosition.toString())
            startActivity(intent)

        }

        var clientPointsB = findViewById<TextView>(R.id.myBillsButton)
        clientPointsB.setOnClickListener {
            val intent = Intent(this,ClientPointsActivity::class.java)
            intent.putExtra("INTENT_NAME",usernameClientMenu.text)
            intent.putExtra("CLIENT_POSITION", clientPosition.toString())
            startActivity(intent)

        }
    }
}