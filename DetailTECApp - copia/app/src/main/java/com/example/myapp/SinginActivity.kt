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
                for (x in 0..locationsCount-1){
                    if (indexLocationsArray[x]==deleteLocation.text.toString()){
                        indexLocationsArray.removeAt(x)
                        locationsArray.removeAt(x)
                        locationsCount--
                        break
                    }
                }
                for (z in 0..locationsCount-1){
                    if (indexLocationsArray[z].toInt()>deleteLocation.text.toString().toInt()){
                        var newNum = indexLocationsArray[z].toInt()-1
                        indexLocationsArray[z] = newNum.toString()
                        println(locationsArray)
                        println(indexLocationsArray)
                    }
                }
                locations.text =""
                deleteLocation.text = ""


                for (y in 0..locationsCount-1){
                    locations.text = locations.text.toString()+"\r\n"+ indexLocationsArray[y] + ". " + locationsArray[y]

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
                for (x in 0..phonesCount-1){
                    if (indexPhonesArray[x]==deletePhone.text.toString()){
                        indexPhonesArray.removeAt(x)
                        phonesArray.removeAt(x)
                        phonesCount--
                        break
                    }
                }
                for (z in 0..phonesCount-1){
                    if (indexPhonesArray[z].toInt()>deletePhone.text.toString().toInt()){
                        var newNum = indexPhonesArray[z].toInt()-1
                        indexPhonesArray[z] = newNum.toString()
                        println(phonesArray)
                        println(indexPhonesArray)
                    }
                }
                phones.text =""
                deletePhone.text = ""


                for (y in 0..phonesCount-1){
                    phones.text = phones.text.toString()+"\r\n"+ indexPhonesArray[y] + ". " + phonesArray[y]
                }
                //println(phonesArray)
                //println(indexPhonesArray)
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



}