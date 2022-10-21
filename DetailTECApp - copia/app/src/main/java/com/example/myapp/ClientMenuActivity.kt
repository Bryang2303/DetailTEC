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
            startActivity(intent)

        }

        var clientPointsB = findViewById<TextView>(R.id.myBillsButton)
        clientPointsB.setOnClickListener {
            val intent = Intent(this,ClientPointsActivity::class.java)
            intent.putExtra("INTENT_NAME",usernameClientMenu.text)
            startActivity(intent)

        }

        /*
        // Boton Abrir la ventana de facturas del cliente
        var clientBillsB = findViewById<TextView>(R.id.myBillsButton)
        clientBillsB.setOnClickListener {
            //val intent = Intent(this,ClientReportActivity::class.java)
            //intent.putExtra("INTENT_NAME",usernameClientMenu.text)
            //startActivity(intent)
            // Request code for selecting a PDF document.
            val PICK_PDF_FILE = 2

            fun openFile(pickerInitialUri: Uri) {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "application/pdf"

                    // Optionally, specify a URI for the file that should appear in the
                    // system file picker when it loads.
                    putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
                }

                startActivityForResult(intent, PICK_PDF_FILE)
            }
            //LoginActivity::class.java
        }*/


    }
}