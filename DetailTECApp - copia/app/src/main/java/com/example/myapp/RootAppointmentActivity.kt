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
import androidx.annotation.RequiresApi
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.lang.Exception
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.*


// Clase de la ventana de solicitud de citas del Administrador
class RootAppointmentActivity : AppCompatActivity() {
    // Variables de texto para la generacion de la factura

    private var connectSql = ConnectSql()

    val STORAGE_CODE = 1001
    var carwashPrice = ""
    var serviceText = ""
    var branchText = ""
    var usernameText = ""
    var plateText = ""
    var line = "------------------------------------------------------------------"
    var line2 = "=================================================================="

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root_appointment)
        // Boton para volver a la ventana del menu del Administrador
        var backAppointmentRootB = findViewById<TextView>(R.id.backAppointmentButtonRoot)
        backAppointmentRootB.setOnClickListener {
            val intent = Intent(this,RootMenuActivity::class.java)
            startActivity(intent)
            //LoginActivity::class.java
        }


        var branchesNamesArray: ArrayList<String> = arrayListOf()
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
                    for (x in 1..2) {
                        //println(result.getString(x))
                        selectedBranchArray.add(result.getString(x))
                        if (x==1){
                            branchesNamesArray.add(result.getString(x)+", "+result.getString(x+1))
                        }


                    }
                    println(selectedBranchArray)
                    selectedBranchArray.clear()
                    result.next()
                }

            }
            Toast.makeText(this,"Datos sobre las citas obtenidos",Toast.LENGTH_SHORT).show()
        } catch (ex: SQLException){
            Toast.makeText(this,"Datos sobre las citas no encontrados", Toast.LENGTH_SHORT).show()
        }

        var carwashesNamesArray: ArrayList<String> = arrayListOf()
        var carwashesPricesArray: ArrayList<String> = arrayListOf()
        try {
            var count = 0

            val querry2: String = "Select count(Tipo) from LAVADO"
            val st2: Statement? = connectSql.dbCon()?.createStatement()
            val result2: ResultSet? = st2?.executeQuery(querry2)
            if (result2 != null) {
                result2.next()
                count = result2.getInt(1)
            }

            val querry: String = "Select Tipo, Precio from LAVADO"
            val st: Statement? = connectSql.dbCon()?.createStatement()
            val result: ResultSet? = st?.executeQuery(querry)

            if (result != null) {
                result.next()

                var selectedCarwashArray: ArrayList<String> = arrayListOf()


                for (x in 1..count) {
                    for (x in 1..2) {
                        //println(result.getString(x))
                        selectedCarwashArray.add(result.getString(x))
                        if (x==1){
                            carwashesNamesArray.add(result.getString(x))
                            carwashesPricesArray.add(result.getString(x+1))
                        }


                    }
                    println(selectedCarwashArray)
                    selectedCarwashArray.clear()
                    result.next()
                }

            }
            Toast.makeText(this,"Datos sobre las citas obtenidos",Toast.LENGTH_SHORT).show()
        } catch (ex: SQLException){
            Toast.makeText(this,"Datos sobre las citas no encontrados", Toast.LENGTH_SHORT).show()
        }



        // Nombre de usuario del cliente y su numero de placa
        var usernameAppointment = findViewById<TextView>(R.id.usernameAppointmentEditTextRoot)
        var plateAppointment = findViewById<TextView>(R.id.plateEditTextRoot)


        /// Array de sucursales disponibles
        val branchs = resources.getStringArray(R.array.branchs)

        // Spinner que permite la seleccion de una sucursal
        val spinner = findViewById<Spinner>(R.id.branchSpinnerRoot)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, branchesNamesArray)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    Toast.makeText(this@RootAppointmentActivity,
                        getString(R.string.selected_item) + " " +
                                "" + branchesNamesArray[position], Toast.LENGTH_SHORT).show()
                    // escribir el texto de la sucursal seleccionada para la posterior factura
                    branchText = branchesNamesArray[position]


                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        // Array de servicios disponibles


        val services = resources.getStringArray(R.array.services)
        println(services)

        // Spinner que permite la seleccion de un servicio
        val spinner2 = findViewById<Spinner>(R.id.serviceSpinnerRoot)
        if (spinner2 != null) {
            val adapter2 = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, carwashesNamesArray)
            spinner2.adapter = adapter2

                spinner2.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    Toast.makeText(this@RootAppointmentActivity,
                        getString(R.string.selected_item) + " " +
                                "" + carwashesNamesArray[position], Toast.LENGTH_SHORT).show()
                    // escribir el texto del servicio seleccionado para la posterior factura
                    serviceText = carwashesNamesArray[position]
                    carwashPrice = carwashesPricesArray[position]
                }


                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        fun insertAppointmentServer(Placa:String, Fecha:String, Sucursal:String,
                               Tipo:String, Cedula:String, Nombre:String, Puntos:String, Monto:String, IVA:String) {
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

                val usuario: PreparedStatement = connectSql.dbCon()?.prepareStatement("insert into CITA values (" +
                        "$Placa, '$Fecha', '$Sucursal', '$Tipo', '$Cedula', '$Nombre', $Puntos, $Monto, $IVA)")!!
                usuario.executeUpdate();
                Toast.makeText(this,"Registros agregados",Toast.LENGTH_SHORT).show()
            } catch (ex: SQLException){
                Log.e("Error: ", ex.message!!)
                Toast.makeText(this,"Registros no insertados", Toast.LENGTH_SHORT).show()
            }
        }
        // Boton para finalzar la asignacion de Cita y generar la factura
        var finishAppointmentRootB = findViewById<TextView>(R.id.appointmentAcceptButtonRoot)
        finishAppointmentRootB.setOnClickListener {

            usernameText = usernameAppointment.text.toString()
            plateText = plateAppointment.text.toString()
            //insertAppointmentServer(plateText,"2022-11-02",branchText,serviceText,usernameText,usernameText,usernameText,)

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                //system OS >= Marshmallow(6.0), verifica si se cuenta conlos permisos
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                    // solicitar el permiso en caso de que no haya sido aceptado aun
                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, STORAGE_CODE)
                }
                else{

                    savePdf()
                }
            }
            else{

                savePdf()
            }
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
            val mText = line2+"\nRECIBO MECATEC\n" + line2 + "\nCliente: " + usernameText + "\n" + line +"\nPlaca: " + plateText + "\n" + line + "\nSucursal: " + branchText + "\n" + line + "\nTipo de lavado: " + serviceText + "                        $"+carwashPrice+"."


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
}