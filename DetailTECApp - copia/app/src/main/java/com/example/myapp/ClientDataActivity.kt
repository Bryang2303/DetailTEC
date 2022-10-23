package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.myapp.models.ClientAddressModel
import com.example.myapp.models.ClientModel
import com.example.myapp.models.ClientPhoneModel

// Clase de la ventana de los datos del Cliente
class ClientDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_data)

        // Iniciar base de datos
        val database = SQLiteHelper(applicationContext)
        val clientsList: ArrayList<ClientModel> = database.getAllClients()

        // Almacenar el nombre del usuario para poder mantenerse logeado
        val bundle = intent.extras
        var clientPosition = bundle?.get("CLIENT_POSITION")
        val name = bundle?.get("INTENT_NAME")


        var usernameDataClient = findViewById<TextView>(R.id.usernameDataTextViewClient)
        // Boton para volver al menu del cliente
        var backClientDataB = findViewById<TextView>(R.id.backDataButtonClient)
        backClientDataB.setOnClickListener {
            val intent = Intent(this,ClientMenuActivity::class.java)
            intent.putExtra("INTENT_NAME",usernameDataClient.text)
            intent.putExtra("CLIENT_POSITION", clientPosition.toString())
            startActivity(intent)

        }

        // Almacenar el nombre del usuario para poder mantenerse logeado
        usernameDataClient.text = name.toString()

        // Cada uno de los datos del cliente, los tomados al momento de registrarse
        var locations = findViewById<TextView>(R.id.locationsTextViewClient)
        var newLocation = findViewById<TextView>(R.id.locationEditTextClient)
        var deleteLocation = findViewById<TextView>(R.id.deleteLocationEditTextClient)

        var phones = findViewById<TextView>(R.id.phonesTextViewClient)
        var newPhone = findViewById<TextView>(R.id.phoneEditTextClient)
        var deletePhone = findViewById<TextView>(R.id.deletePhoneEditTextClient)

        var fName = findViewById<TextView>(R.id.nameEditTextClient)
        var lName = findViewById<TextView>(R.id.lastNameEditTextClient)
        var lName2 = findViewById<TextView>(R.id.lastNameEditText2Client)
        var id = findViewById<TextView>(R.id.idEditTextClient)
        var email = findViewById<TextView>(R.id.emailEditTextClient)

        // Coloca la informacion en pantalla
        fName.text = clientsList.get(Integer.parseInt(clientPosition.toString())).name
        id.text = clientsList.get(Integer.parseInt(clientPosition.toString())).id.toString()
        email.text = clientsList.get(Integer.parseInt(clientPosition.toString())).email

        // Array de los datos del cliente
        var clientData2: ArrayList<TextView> = arrayListOf()
        clientData2.addAll(listOf(fName,lName,lName2,id,email,locations,phones))

        // Contador de ubicaciones del cliente, al ser multivaluado, el algoritmo permite la adicion o eliminacion de tantas direcciones como sea posible
        var locationsCount = 0

        var locationsArray: ArrayList<String> = arrayListOf()
        var indexLocationsArray: ArrayList<String> = arrayListOf()

        // Boton para agregar una direccion nueva
        var addlocationSinginIB = findViewById<ImageButton>(R.id.addlocationImageButtonClient)
        addlocationSinginIB.setOnClickListener {

            if (locations.text == "" || locations.text.isEmpty()){
                locationsArray.add(newLocation.text.toString())
                indexLocationsArray.add((locationsCount+1).toString())


                //locationsMap[locationsCount] = newLocation.text.toString()
                locations.text = (locationsCount+1).toString()+". " + newLocation.text.toString()
                locationsCount++
            } else {
                //locationsMap[locationsCount] = newLocation.text.toString()
                locationsCount = locationsArray.size + 1
                locationsArray.add(newLocation.text.toString())
                indexLocationsArray.add((locationsCount+1).toString())

                locations.text = locations.text.toString()+"\r\n"+ (locationsCount+1).toString()+". "+ newLocation.text.toString()
                locationsCount++
            }
            newLocation.text = ""
        }
        // Boton para eliminar una direccion correspondiente
        var deletelocationsSinginIB = findViewById<ImageButton>(R.id.deleteLocationImageButtonClient)
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
        var addphonesSinginIB = findViewById<ImageButton>(R.id.addphoneImageButtonClient)
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
        var deletephonesSinginIB = findViewById<ImageButton>(R.id.deletePhoneImageButtonClient)
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

        var clientPhones: ArrayList<ClientPhoneModel> = database.getAllClientPhones()
        var clientAddresses: ArrayList<ClientAddressModel> = database.getAllClientAddresses()

        var positionCounter = 0
        var itemsCounter = 0
        for (i in clientPhones) {
            if (id.text.toString() == clientPhones.get(positionCounter).id.toString()) {
                if (phones.text == "" || phones.text.isEmpty()) {
                    phones.text = (itemsCounter+1).toString() + ". " + clientPhones.get(positionCounter).phone
                    phonesArray.add(clientPhones.get(positionCounter).phone)
                    itemsCounter++
                } else {
                    phones.text = phones.text.toString() + "\r\n" + (itemsCounter+1).toString() + ". " + clientPhones.get(positionCounter).phone
                    itemsCounter++
                }
            }

            positionCounter++
        }

        positionCounter = 0
        itemsCounter = 0
        for (i in clientAddresses) {
            if (id.text.toString() == clientAddresses.get(positionCounter).id.toString()) {
                if (locations.text == "" || phones.text.isEmpty()) {
                    locations.text = (itemsCounter+1).toString() + ". " + clientAddresses.get(positionCounter).address
                    locationsArray.add(clientAddresses.get(positionCounter).address)
                    itemsCounter++
                } else {
                    locations.text = locations.text.toString() + "\r\n" + (itemsCounter+1).toString() + ". " + clientAddresses.get(positionCounter).address
                    itemsCounter++
                }
            }

            positionCounter++
        }

        var updateClientDataB = findViewById<Button>(R.id.dataAcceptButtonClient)
        updateClientDataB.setOnClickListener {
            val clientPos = Integer.parseInt(clientPosition.toString())
            var id = clientsList.get(clientPos).id
            var name = fName.text.toString()
            var username = usernameDataClient.text.toString()
            var password = clientsList.get(clientPos).password
            var email = email.text.toString()
            var points = clientsList.get(clientPos).points

            val client = ClientModel(id = id, name = name, user = username, password = password,
                email = email, points = points)

            updateClientPhones(database, id, phonesArray)

            positionCounter = 0
            for (i in locationsArray) {
                val clientAddress = ClientAddressModel(id = id, address = locationsArray.get(positionCounter))
//                database.insertClientAddress(clientAddress)
                positionCounter++
            }

            if (database.updateClient(client) < 0) {
                showErrorMessage()
            } else {
                showUpdatedMessage()
            }
        }
    }

    fun updateClientPhones(database: SQLiteHelper, id: Int, phones: ArrayList<String>) {
        // Revisar números existentes
        val phonesList = database.getAllClientPhones()
        var firstCounter = 0
        var notExisting = true
        for (i in phonesList) {
            var secondCounter = 0
            while (secondCounter < phones.size && notExisting) {
                if (id == phonesList.get(firstCounter).id && phonesList.get(firstCounter).phone == phones.get(secondCounter)) {
                    notExisting = false
                }
                secondCounter++
            }

            if (notExisting) {
                database.deleteClientPhone(id, phonesList.get(firstCounter).phone)
            }
            notExisting = true
            firstCounter++
        }

        firstCounter = 0
        notExisting = true
        for (i in phones) {
            var secondCounter = 0
            while (secondCounter < phonesList.size && notExisting) {
                if (id == phonesList.get(secondCounter).id && phones.get(firstCounter) == phonesList.get(secondCounter).phone) {
                    notExisting = false
                }

                secondCounter++
            }

            if (notExisting) {
                val clientPhone = ClientPhoneModel(id =  id, phone = phones.get(firstCounter))
                database.insertClientPhone(clientPhone)
            }
            notExisting = true
            firstCounter++
        }
    }

    fun checkExistingPhone(database: SQLiteHelper, id: Int, phone: String): Boolean {
        val clientPhoneList: ArrayList<ClientPhoneModel> = database.getAllClientPhones()
        var counter = 0
        for (i in clientPhoneList) {
            if (clientPhoneList.get(counter).id == id && clientPhoneList.get(counter).phone == phone) {
                return false
            }
            counter++
        }
        return true
    }

    fun showErrorMessage(){
        Toast.makeText(this,"Ha ocurrido un error en la actualización de datos", Toast.LENGTH_SHORT).show()
    }

    fun showUpdatedMessage(){
        Toast.makeText(this,"Datos actualizados exitósamente", Toast.LENGTH_SHORT).show()
    }
}