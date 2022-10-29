package com.example.myapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.myapp.models.AppointmentModel
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*
// Clase de la ventana de solicitud de citas del Cliente
class ClientAppointmentActivity : AppCompatActivity() {
    // Variables de texto para la generacion de la factura
    val STORAGE_CODE = 1001
    var serviceText = ""
    var branchText = ""
    var usernameText = ""
    var plateText = ""
    var line = "------------------------------------------------------------------"
    var line2 = "=================================================================="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_appointment)

        var usernameAppointmentClient = findViewById<TextView>(R.id.usernameAppointmentTextViewClient)

        val database = SQLiteHelper(applicationContext)
        val clientList = database.getAllClients()

        // Almacenar el nombre del usuario para poder mantenerse logeado
        val bundle = intent.extras
        val name = bundle?.get("INTENT_NAME")
        var clientPosition = bundle?.get("CLIENT_POSITION")

        val clientInfo = clientList.get(Integer.parseInt(clientPosition.toString()))
        var selectedBranch = ""
        var selectedService = ""

        // Boton para volver a la ventana del menu del cliente
        var backClientAppointmentB = findViewById<TextView>(R.id.backAppointmentButtonClient)
        backClientAppointmentB.setOnClickListener {
            val intent = Intent(this,ClientMenuActivity::class.java)
            intent.putExtra("INTENT_NAME",usernameAppointmentClient.text)
            intent.putExtra("CLIENT_POSITION", clientPosition.toString())
            startActivity(intent)
            //LoginActivity::class.java
        }

        var plateAppointmentClient = findViewById<TextView>(R.id.plateEditTextClient)

        //var usernameClientAppointment = findViewById<TextView>(R.id.usernameAppointmentTextViewClient)
        usernameAppointmentClient.text = name.toString()

        // Array de sucursales disponibles
        val branchs = resources.getStringArray(R.array.branchs)

        // Spinner que permite la seleccion de una sucursal
        val spinner = findViewById<Spinner>(R.id.branchSpinnerClient)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, branchs)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    selectedBranch = branchs[position]
                        Toast.makeText(this@ClientAppointmentActivity,
                            getString(R.string.selected_item) + " " +
                                    "" + branchs[position], Toast.LENGTH_SHORT).show()
                    // escribir el texto de la sucursal seleccionada para la posterior factura
                    branchText = branchs[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        // Array de servicios disponibles
        val services = resources.getStringArray(R.array.services)

        // Spinner que permite la seleccion de un servicio
        val spinner2 = findViewById<Spinner>(R.id.serviceSpinnerClient)
        if (spinner2 != null) {
            val adapter2 = ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, services)
            spinner2.adapter = adapter2

            spinner2.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    selectedService = services[position]
                    Toast.makeText(this@ClientAppointmentActivity,
                            getString(R.string.selected_item) + " " +
                                    "" + services[position], Toast.LENGTH_SHORT).show()
                    // escribir el texto del servicio seleccionado para la posterior factura
                    serviceText = services[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        // Boton para finalzar la solicitud de Cita y generar la factura
        var finishAppointmentClientB = findViewById<TextView>(R.id.appointmentAcceptButtonClient)
        finishAppointmentClientB.setOnClickListener {
            var appointment: AppointmentModel

            if (plateAppointmentClient.text == "" || plateAppointmentClient.text.isEmpty()) {
                showPlateMessage()
            } else {
                var carId = plateAppointmentClient.text.toString()
                var date = "--"
                var type = selectedService
                var name = clientInfo.name
                var id = clientInfo.id
                var branchName = selectedBranch

                appointment = AppointmentModel(carId = carId, date = date, type = type, name = name,
                    id = id, branchName = branchName)
            }

//            usernameText = usernameAppointmentClient.text.toString()
//            plateText = plateAppointmentClient.text.toString()
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
//                //system OS >= Marshmallow(6.0), verifica si se cuenta conlos permisos
//                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    == PackageManager.PERMISSION_DENIED){
//                    // solicitar el permiso en caso de que no haya sido aceptado aun
//                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    requestPermissions(permissions, STORAGE_CODE)
//                }
//                else{
//
//                    savePdf()
//                }
//            }
//            else{
//
//                savePdf()
//            }
        }
    }
    // Funcion que permite la generacion de una factura pdf para su posterior almacenado en el dospositivo
    private fun savePdf() {

        val mDoc = Document()
        //nombre de las facturas
        val mFileName = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        //Path de las facturas
        val mFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFileName +".pdf"
        try {
            //Crear instancia para el lector del pdf
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))

            //Abrir el documento para escritura
            mDoc.open()

            //Generaar el texto que se mostrara en la factura
            val mText = line2+"\nRECIBO MECATEC\n" + line2 + "\nCliente: " + usernameText + "\n" + line +"\nPlaca: " + plateText + "\n" + line + "\nSucursal: " + branchText + "\n" + line + "\nTipo de lavado: " + serviceText + "                        $10.000."


            mDoc.addAuthor("Atif Pervaiz")

            //Agregar el texto a la factura
            mDoc.add(Paragraph(mText))

            //cerrar documento
            mDoc.close()

            //Muestra un mensaje del archivo guardado con su respectivo path
            Toast.makeText(this, "$mFileName.pdf\nis saved to\n$mFilePath", Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception){
            //Mensaje de error, en caso de que algo salga mal
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }
    // Funcion que permite verificar el acceso del usuario al almacenamiento externo del dispositivo
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            STORAGE_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted, call savePdf() method
                    savePdf()
                }
                else{
                    //permission from popup was denied, show error message
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun showPlateMessage() {
        Toast.makeText(this, "Ingrese una placa de vehiculo", Toast.LENGTH_SHORT).show()
    }
}