package com.example.myapp

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.myapp.models.ClientAddressModel
import com.example.myapp.models.ClientModel
import com.example.myapp.models.ClientPhoneModel
import java.lang.Exception

// Clase de la ventana de la gestion de Clientes
class RootClientmanagementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root_clientmanagement)

        // Se inicia la base de datos
        val database = SQLiteHelper(applicationContext)
        var clientID = ""
        var clientPosition = -1

        // Boton para volver al menu del Administrador
        var backClientDataRootB = findViewById<TextView>(R.id.backDataButtonRoot)
        backClientDataRootB.setOnClickListener {
            val intent = Intent(this,RootMenuActivity::class.java)
            startActivity(intent)
            //LoginActivity::class.java
        }

        // Datos de un respectivo cliente
        var locations = findViewById<TextView>(R.id.locationsTextViewRoot)
        var newLocation = findViewById<TextView>(R.id.locationEditTextRoot)
        var deleteLocation = findViewById<TextView>(R.id.deleteLocationEditTextRoot)

        var phones = findViewById<TextView>(R.id.phonesTextViewRoot)
        var newPhone = findViewById<TextView>(R.id.phoneEditTextRoot)
        var deletePhone = findViewById<TextView>(R.id.deletePhoneEditTextRoot)

        var fName = findViewById<TextView>(R.id.nameEditTextRoot)
        var username = findViewById<TextView>(R.id.usernameEditTextRoot)
        var lName2 = findViewById<TextView>(R.id.lastNameEditText2Root)
        var id = findViewById<TextView>(R.id.idEditTextRoot)
        var email = findViewById<TextView>(R.id.emailEditTextRoot)

        //var addlocationImageButton = findViewById (R.id.addlocationImageButton)

        var clientData2: ArrayList<TextView> = arrayListOf()
        clientData2.addAll(listOf(fName,username,email,locations,phones))

        // Contador de ubicaciones del cliente, al ser multivaluado, el algoritmo permite la adicion o eliminacion de tantas direcciones como sea posible
        var locationsCount = 0

        var locationsArray: ArrayList<String> = arrayListOf()
        var indexLocationsArray: ArrayList<String> = arrayListOf()
        // Boton para agregar una direccion nueva
        var addlocationSinginIB = findViewById<ImageButton>(R.id.addlocationImageButtonRoot)
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
        var deletelocationsSinginIB = findViewById<ImageButton>(R.id.deleteLocationImageButtonRoot)
        deletelocationsSinginIB.setOnClickListener {

            if (locations.text == "" || locations.text.isEmpty()){

                // restriccion de int
            } else {
                // Remover una direccion
                var readyToDelete = false
                var locationPosition = -1

                try {
                    // Intenta parsear el valor ingresado por el usuario
                    locationPosition = Integer.parseInt(deleteLocation.text.toString())
                    readyToDelete = true
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                if (readyToDelete) {
                    if ((locationPosition-1) < locationsArray.size) {
                        // Eliminacion de item del arreglo interno
                        locationsArray.removeAt(locationPosition-1)
                        showRemovedMessage()
                    } else {
                        showErrorMessage()
                    }
                    var position = 0
                    // Actualizacion de items en pantalla
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
        var addphonesSinginIB = findViewById<ImageButton>(R.id.addphoneImageButtonRoot)
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
        var deletephonesSinginIB = findViewById<ImageButton>(R.id.deletePhoneImageButtonRoot)
        deletephonesSinginIB.setOnClickListener {

            if (phones.text == "" || phones.text.isEmpty()){

                // restriccion de int
            } else {
                // Remover un telefono
                var readyToDelete = false
                var phonePosition = -1

                try {
                    // Intenta parsear el valor ingresado por el usuario
                    phonePosition = Integer.parseInt(deletePhone.text.toString())
                    readyToDelete = true
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                if (readyToDelete) {
                    if ((phonePosition-1) < phonesArray.size) {
                        // Eliminacion de item en el arreglo interno
                        phonesArray.removeAt(phonePosition-1)
                        showRemovedMessage()
                    } else {
                        showErrorMessage()
                    }
                    var position = 0
                    // Actualizacion de items en pantalla
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

        // Boton para buscar a un cliente por su nombre de usuario
        var searchClientIB = findViewById<ImageButton>(R.id.searchClientImageButtonRoot)
        searchClientIB.setOnClickListener {
            // Reinicia los arreglos con items del cliente
            locationsArray.removeAll(locationsArray)
            phonesArray.removeAll(phonesArray)
            // Ubica la posicion del usuarrio en los registros de la base de datos
            clientPosition = setClientInfo(database, id.text.toString(), clientData2)

            if (clientPosition > -1) {
                // Colocacion de informacion en pantalla
                val clientList: ArrayList<ClientModel> = database.getAllClients()
                clientID = clientList.get(clientPosition).id
                locations.text = ""
                phones.text = ""
                val locationsList: ArrayList<ClientAddressModel> = database.getAllClientAddresses()
                val phonesList: ArrayList<ClientPhoneModel> = database.getAllClientPhones()
                var counter = 0
                var itemsCounter = 0
                for (i in locationsList) {
                    if (locationsList.get(counter).id.toString() == id.text.toString()) {
                        locationsArray.add(locationsList.get(counter).address)
                        if (locations.text == "" || locations.text.isEmpty()) {
                            locations.text = (itemsCounter+1).toString() + ". " + locationsList.get(counter).address
                            indexLocationsArray.add((itemsCounter+1).toString())
                            itemsCounter++
                        } else {
                            locations.text = locations.text.toString() + "\r\n" + (itemsCounter+1).toString() + ". " + locationsList.get(counter).address
                            indexLocationsArray.add((itemsCounter+1).toString())
                            itemsCounter++
                        }
                    }

                    counter++
                }

                counter = 0
                itemsCounter = 0
                for (i in phonesList) {
                    if (phonesList.get(counter).id.toString() == id.text.toString()) {
                        phonesArray.add(phonesList.get(counter).phone)
                        if (phones.text == "" || phones.text.isEmpty()) {
                            phones.text = (itemsCounter+1).toString() + ". " + phonesList.get(counter).phone
                            indexPhonesArray.add((itemsCounter+1).toString())
                            itemsCounter++
                        } else {
                            phones.text = phones.text.toString() + "\r\n" + (itemsCounter+1).toString() + ". " + phonesList.get(counter).phone
                            indexPhonesArray.add((itemsCounter+1).toString())
                            itemsCounter++
                        }
                    }

                    counter++
                }
            }
        }

        var updateClientDataB = findViewById<Button>(R.id.dataAcceptButtonRoot)
        updateClientDataB.setOnClickListener {
            var readyToSave = true

            // Verificacion de espacios vacios
            for (dataInput in clientData2) {
                if (dataInput.text == "" && dataInput.text.isEmpty()) {
                    readyToSave = false
                    showErrorMessage()
                    break
                }
            }

            if (readyToSave) {
                // Proceso de actualizacion tras verificacion
                val clientList: ArrayList<ClientModel> = database.getAllClients()
                var newName = fName.text.toString()
                var user = username.text.toString()
                var password = clientList.get(clientPosition).password
                var email = email.text.toString()
                var points = clientList.get(clientPosition).points

                val client = ClientModel(id = clientID, name = newName, user = user, password = password,
                    email = email, points = points)

                updateClientAddresses(database, clientID, locationsArray)
                updateClientPhones(database, clientID, phonesArray)

                if (database.updateClient(client) < 0) {
                    showErrorMessage()
                } else {
                    showUpdatedMessage()
                }
            }
        }
    }

    // Colocacion de informacion del cliente en la pantalla
    private fun setClientInfo(database: SQLiteHelper, id: String, clientData: ArrayList<TextView>): Int {
        val clientsList: ArrayList<ClientModel> = database.getAllClients()
        var clientPosition = -1

        var counter = 0
        for (i in clientsList) {
            if (clientsList.get(counter).id == id) {
                clientPosition = counter
            }

            counter++
        }

        if (clientPosition > -1) {
            clientData.get(0).text = clientsList.get(clientPosition).name
            clientData.get(1).text = clientsList.get(clientPosition).user
            clientData.get(2).text = clientsList.get(clientPosition).email
        }

        return clientPosition
    }

    // Actualizacion de direcciones
    private fun updateClientAddresses(database: SQLiteHelper, id: String, locations:ArrayList<String>) {
        database.deleteClientAddress(id)
        var counter = 0
        for (i in locations) {
            val clientAddress = ClientAddressModel(id = id, address = locations.get(counter))
            database.insertClientAddress(clientAddress)
            counter++
        }
//        val locationList = database.getAllClientAddresses()
//        var firstCounter = 0
//        var notExisting = true
//        for (i in locationList) {
//            var secondCounter = 0
//            while (secondCounter < locations.size && notExisting) {
//                if (id == locationList.get(firstCounter).id && locationList.get(firstCounter).address == locations.get(secondCounter)) {
//                    notExisting = false
//                }
//
//                secondCounter++
//            }
//
//            if (id == locationList.get(firstCounter).id && notExisting) {
//                database.deleteClientAddress(id)
//            }
//            notExisting = true
//            firstCounter++
//        }
//
//        firstCounter = 0
//        notExisting = true
//        for (i in locations) {
//            var secondCounter = 0
//            while (secondCounter < locationList.size && notExisting) {
//                if (id == locationList.get(secondCounter).id && locations.get(firstCounter) == locationList.get(secondCounter).address) {
//                    notExisting = false
//                }
//
//                secondCounter++
//            }
//
//            if (notExisting) {
//                val clientAddress = ClientAddressModel(id = id, address = locations.get(firstCounter))
//                database.insertClientAddress(clientAddress)
//            }
//
//            notExisting = true
//            firstCounter++
//        }
    }

    // Actualizacion de telefonos
    private fun updateClientPhones(database: SQLiteHelper, id: String, phones: ArrayList<String>) {
        database.deleteClientPhone(id)
        var counter = 0
        for (i in phones) {
            val clientPhone = ClientPhoneModel(id = id, phone = phones.get(counter))
            database.insertClientPhone(clientPhone)
            counter++
        }
    }
        // Revisar números existentes
//        val phonesList = database.getAllClientPhones()
//        var firstCounter = 0
//        var notExisting = true
//        for (i in phonesList) {
//            var secondCounter = 0
//            while (secondCounter < phones.size && notExisting) {
//                if (id == phonesList.get(firstCounter).id && phonesList.get(firstCounter).phone == phones.get(secondCounter)) {
//                    notExisting = false
//                }
//                secondCounter++
//            }
//
//            if (id == phonesList.get(firstCounter).id && notExisting) {
//                database.deleteClientPhone(id)
//            }
//            notExisting = true
//            firstCounter++
//        }
//
//        firstCounter = 0
//        notExisting = true
//        for (i in phones) {
//            var secondCounter = 0
//            while (secondCounter < phonesList.size && notExisting) {
//                if (id == phonesList.get(secondCounter).id && phones.get(firstCounter) == phonesList.get(secondCounter).phone) {
//                    notExisting = false
//                }
//
//                secondCounter++
//            }
//
//            if (notExisting) {
//                val clientPhone = ClientPhoneModel(id =  id, phone = phones.get(firstCounter))
//                database.insertClientPhone(clientPhone)
//            }
//            notExisting = true
//            firstCounter++
//        }
//    }

    fun showRemovedMessage() {
        Toast.makeText(this, "Item eliminado", Toast.LENGTH_SHORT).show()
    }

    fun showErrorMessage(){
        Toast.makeText(this,"Ha ocurrido un error en la actualización de datos", Toast.LENGTH_SHORT).show()
    }

    fun showUpdatedMessage(){
        Toast.makeText(this,"Datos actualizados exitósamente", Toast.LENGTH_SHORT).show()
    }
}