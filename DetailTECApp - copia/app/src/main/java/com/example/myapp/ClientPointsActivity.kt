package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ClientPointsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_points)

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

        usernamePointsClient.text = name.toString()
    }
}