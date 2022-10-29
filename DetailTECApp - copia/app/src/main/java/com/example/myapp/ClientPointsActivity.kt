package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.myapp.models.ClientModel

class ClientPointsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_points)

        // Inicia la base de datos y toma una lista de todos los clientes registrados
        val database = SQLiteHelper(applicationContext)
        val clientList: ArrayList<ClientModel> = database.getAllClients()

        var usernamePointsClient = findViewById<TextView>(R.id.usernamePointsTextViewClient)
        val bundle = intent.extras
        val name = bundle?.get("INTENT_NAME")
        var clientPosition = bundle?.get("CLIENT_POSITION")

        var backClientPointsB = findViewById<TextView>(R.id.backPointsButtonClient)
        backClientPointsB.setOnClickListener {
            val intent = Intent(this,ClientMenuActivity::class.java)
            intent.putExtra("INTENT_NAME",usernamePointsClient.text)
            intent.putExtra("CLIENT_POSITION", clientPosition.toString())
            startActivity(intent)

        }

        // Crea un objeto con toda la informacion del cliente
        val clientInfo = clientList.get(Integer.parseInt(clientPosition.toString()))

        val availablePoints = findViewById<TextView>(R.id.availablePointstxt)
        val redeemedPoints = findViewById<TextView>(R.id.redeemedPointstxt)

        // Asignacion de los valores del cliente en pantalla
        availablePoints.text = clientInfo.points.toString()
        redeemedPoints.text = "0"

        usernamePointsClient.text = name.toString()
    }
}