package com.example.myapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.ImageButton
import android.widget.Toast
import com.example.myapp.models.ClientAddressModel
import com.example.myapp.models.ClientModel
import com.example.myapp.models.ClientPhoneModel
import java.lang.Exception

// Clase de la ventana de inicio de sesion
class SinginActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singin)

        // Recibe base de datos
        val database = SQLiteHelper(applicationContext)

        // Boton para volver a la ventana Main
        var backSingInB = findViewById<TextView>(R.id.backSinginButton)
        backSingInB.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            //LoginActivity::class.java
        }
        // Datos que son tomados por la base de datos al momento de que un cliente se registra
        var locations = findViewById<TextView>(R.id.locationsTextView)
        var newLocation = findViewById<TextView>(R.id.locationEditText)
        var deleteLocation = findViewById<TextView>(R.id.deleteLocationEditText)

        var phones = findViewById<TextView>(R.id.phonesTextView)
        var newPhone = findViewById<TextView>(R.id.phoneEditText)
        var deletePhone = findViewById<TextView>(R.id.deletePhoneEditText)

        var fName = findViewById<TextView>(R.id.nameEditText)
        var lName = findViewById<TextView>(R.id.lastNameEditText)
        var lName2 = findViewById<TextView>(R.id.lastNameEditText2)
        var id = findViewById<TextView>(R.id.idEditText)
        var email = findViewById<TextView>(R.id.emailEditText)
        var singinUsername = findViewById<TextView>(R.id.usernameSinginEditText)
        var singinPassword = findViewById<TextView>(R.id.passwordSinginEditText)
        //var addlocationImageButton = findViewById (R.id.addlocationImageButton)

        var clientData: ArrayList<TextView> = arrayListOf()
        clientData.addAll(listOf(fName,id,email,singinUsername,singinPassword,locations,phones))

        // Contador de ubicaciones del cliente, al ser multivaluado, el algoritmo permite la adicion o eliminacion de tantas direcciones como sea posible
        var locationsCount = 0

        var locationsArray: ArrayList<String> = arrayListOf()
        var indexLocationsArray: ArrayList<String> = arrayListOf()
        // Boton para agregar una direccion nueva
        var addlocationSinginIB = findViewById<ImageButton>(R.id.addlocationImageButton)
        addlocationSinginIB.setOnClickListener {

            if (locations.text == "" || locations.text.isEmpty()){
                locationsArray.add(newLocation.text.toString())
                indexLocationsArray.add((locationsCount+1).toString())


                //locationsMap[locationsCount] = newLocation.text.toString()
                locations.text = (locationsCount+1).toString()+". " + newLocation.text.toString()
                locationsCount++
            } else {
                //locationsMap[locationsCount] = newLocation.text.toString()
                locationsCount = locationsArray.size
                locationsArray.add(newLocation.text.toString())
                indexLocationsArray.add((locationsCount+1).toString())

                locations.text = locations.text.toString()+"\r\n"+ (locationsCount+1).toString()+". "+ newLocation.text.toString()
                locationsCount++
            }
            newLocation.text = ""
        }
        // Boton para eliminar una direccion correspondiente
        var deletelocationsSinginIB = findViewById<ImageButton>(R.id.deleteLocationImageButton)
        deletelocationsSinginIB.setOnClickListener {

            if (locations.text == "" || locations.text.isEmpty()){

                // restriccion de int
            } else {
                // Remover una direccion
                var readyToDelete = false
                var locationPosition = -1

                try {
                    locationPosition = Integer.parseInt(deleteLocation.text.toString())
                    readyToDelete = true
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                if (readyToDelete) {
                    if ((locationPosition-1) < locationsArray.size) {
                        locationsArray.removeAt(locationPosition-1)
                        showRemovedMessage()
                    } else {
                        showErrorMessage()
                    }
                    var position = 0
                    locations.text = ""
                    for (x in locationsArray) {
                        if (position == 0) {
                            locations.text = (position+1).toString() + ". " + locationsArray.get(position)
                        } else {
                            locations.text = locations.text.toString() + "\r\n" + (position+1).toString() + ". " + locationsArray.get(position)
                        }

                        position++
                    }
                }
            }
        }
        // Contador de telefonos del cliente, al ser multivaluado, el algoritmo permite la adicion o eliminacion de tantos numeros telefonicos como sea posible
        var phonesCount = 0

        var phonesArray: ArrayList<String> = arrayListOf()
        var indexPhonesArray: ArrayList<String> = arrayListOf()
        // Boton para agregar un telefono nuevo
        var addphonesSinginIB = findViewById<ImageButton>(R.id.addphoneImageButton)
        addphonesSinginIB.setOnClickListener {

            if (phones.text == "" || phones.text.isEmpty()){
                phonesArray.add(newPhone.text.toString())
                indexPhonesArray.add((phonesCount+1).toString())

                //locationsMap[locationsCount] = newLocation.text.toString()
                phones.text = (phonesCount+1).toString()+". " + newPhone.text.toString()
                phonesCount++
            } else {
                //locationsMap[locationsCount] = newLocation.text.toString()
                phonesCount = phonesArray.size
                phonesArray.add(newPhone.text.toString())
                indexPhonesArray.add((phonesCount+1).toString())

                phones.text = phones.text.toString()+"\r\n"+ (phonesCount+1).toString()+". "+ newPhone.text.toString()
                phonesCount++
            }
            newPhone.text = ""

        }
        // Boton para eliminar un telefono correspondiente
        var deletephonesSinginIB = findViewById<ImageButton>(R.id.deletePhoneImageButton)
        deletephonesSinginIB.setOnClickListener {

            if (phones.text == "" || phones.text.isEmpty()){

                // restriccion de int
            } else {
                // Remover un telefono
                var readyToDelete = false
                var phonePosition = -1

                try {
                    phonePosition = Integer.parseInt(deletePhone.text.toString())
                    readyToDelete = true
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                if (readyToDelete) {
                    if ((phonePosition-1) < phonesArray.size) {
                        phonesArray.removeAt(phonePosition-1)
                        showRemovedMessage()
                    } else {
                        showErrorMessage()
                    }
                    var position = 0
                    phones.text = ""
                    for (x in phonesArray) {
                        if (position == 0) {
                            phones.text = (position+1).toString() + ". " + phonesArray.get(position)
                        } else {
                            phones.text = phones.text.toString() + "\r\n" + (position+1).toString() + ". " + phonesArray.get(position)
                        }

                        position++
                    }
                }
            }

        }
        // Boton para proceder luego del registro
        var proceedSingInB = findViewById<TextView>(R.id.SinginAcceptButton)
        proceedSingInB.setOnClickListener {
            var readyToSave = true

            for (dataInput in clientData){
                if (dataInput.text == "" || dataInput.text.isEmpty()){
                    readyToSave = false
                    showErrorSingin()
                    break
                }
//                else {
//                    val intent = Intent(this,LoginActivity::class.java)
//                    startActivity(intent)
//                    //dataInput.text = "FUNCIONA"
//
//                }
            }

            // Almacenamiento en base de datos
            if (readyToSave) {
                var name = clientData[0].text.toString()
                var id = clientData[1].text.toString()
                var email = clientData[2].text.toString()
                var username = clientData[3].text.toString()
                var password = clientData[4].text.toString()

                val client = ClientModel(id = id, name = name, user = username, password = password,
                    email = email, points = 0)

                val success = database.insertClient(client)

                if (success > 0) {
                    addPhonesAndLocations(locationsArray, phonesArray, database, id)
                    createdMessage()
                } else {
                    existingMessage()
                }
            }


        }


    }

    private fun addPhonesAndLocations(
        locationsArray: ArrayList<String>,
        phonesArray: ArrayList<String>,
        database: SQLiteHelper,
        id: String
    ) {
        var counter = 0
        for (i in locationsArray) {
            val clientAddress = ClientAddressModel(id = id, address =  locationsArray[counter])
            database.insertClientAddress(clientAddress)
            counter++
        }
        counter = 0
        for (i in phonesArray) {
            val clientPhone = ClientPhoneModel(id = id, phone = phonesArray[counter])
            database.insertClientPhone(clientPhone)
            counter++
        }
    }

    fun maping(){
        var myMap:MutableMap<String, Int>


        myMap = mutableMapOf("Juan" to 1, "Pedro" to 2, "Miguel" to 3)
        println(myMap)

        myMap["Ana"] = 7 // Meter valores al map
        myMap.put("Maria", 8)
        println(myMap)

        println(myMap["Ana"])

        myMap.remove("Maria")
        println(myMap)
    }

    // Si el registro falla, muestra un mensaje de error
    fun showErrorSingin(){
        Toast.makeText(this,"Asegurese de haber completado todos sus datos", Toast.LENGTH_SHORT).show()
    }

    // Mensaje al registrar un usuario
    fun createdMessage(){
        Toast.makeText(this,"Usuario creado de manera exitosa", Toast.LENGTH_SHORT).show()
    }

    // Mensaje si el usuario ya existe en la base de datos
    fun existingMessage(){
        Toast.makeText(this,"El usuario ingresado ya existe", Toast.LENGTH_SHORT).show()
    }

    fun showRemovedMessage() {
        Toast.makeText(this, "Item eliminado", Toast.LENGTH_SHORT).show()
    }

    fun showErrorMessage() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

}