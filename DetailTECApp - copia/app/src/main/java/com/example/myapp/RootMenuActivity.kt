package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
// Clase de la ventana de menu para el Administrador
class RootMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root_menu)

        // Boton para volver a la pantalla de inicio de sesion
        var backRootMenuB = findViewById<TextView>(R.id.singoffRootButton)
        backRootMenuB.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            //LoginActivity::class.java
        }
        // Boton para acceder a la ventana de gestion de clientes
        var clientManagementB = findViewById<TextView>(R.id.clientsRootButton)
        clientManagementB.setOnClickListener {
            val intent = Intent(this,RootClientmanagementActivity::class.java)
            startActivity(intent)
            //LoginActivity::class.java
        }
        // Boton para acceder a la pamtalla de asignacion de citas
        var rootAppointmentB = findViewById<TextView>(R.id.appointmentsRootButton)
        rootAppointmentB.setOnClickListener {
            val intent = Intent(this,RootAppointmentActivity::class.java)
            startActivity(intent)
            //LoginActivity::class.java
        }
    }
}