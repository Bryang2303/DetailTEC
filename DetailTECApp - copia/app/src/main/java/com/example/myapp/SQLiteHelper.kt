package com.example.myapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapp.models.*
import java.lang.Exception

class SQLiteHelper (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "detailTec.db"
        private const val TBL_PROVIDER = "tbl_provider"
        private const val TBL_INPUT_PRODUCT = "tbl_input_product"
        private const val TBL_WORKER = "tbl_worker"
        private const val TBL_BRANCH_OFFICE = "tbl_branch_office"
        private const val TBL_WASH = "tbl_wash"
        private const val TBL_CLIENT = "tbl_client"
        private const val TBL_APPOINTMENT = "tbl_appointment"
        private const val TBL_WORKER_BRANCH = "tbl_worker_branch"
        private const val TBL_PROVIDER_INPUT = "tbl_provider_input"
        private const val TBL_INPUT_PRODUCT_WASH = "tbl_input_product_wash"
        private const val TBL_CLIENT_PHONE = "tbl_client_phone"
        private const val TBL_CLIENT_ADDRESS = "tbl_client_address"
        private const val TBL_APPOINTMENT_WORKER = "tbl_appointment_worker"
        private const val TBL_APPOINTMENT_INPUT = "tbl_appointment_input"
        private const val LEGAL_IDENTITY = "legal_identity"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val ADDRESS = "address"
        private const val CONTACT = "contact"
        private const val BRAND = "brand"
        private const val PRICE = "price"
        private const val ID = "id"
        private const val L_NAME1 = "l_name1"
        private const val L_NAME2 = "l_name2"
        private const val BIRTHDATE = "birthdate"
        private const val AGE = "age" //Derived attribute
        private const val ROL = "rol"
        private const val PAYMENT_METHOD = "payment_method"
        private const val PASSWORD = "password"
        private const val START_DATE = "start_date"
        private const val PHONE = "phone"
        private const val PROVINCE = "province"
        private const val CANTON = "canton"
        private const val DISTRICT = "district"
        private const val OPEN_DATE = "open_date"
        private const val TYPE = "type"
        private const val ESTIMATED_DURATION = "estimated_duration"
        private const val POINTS_NEEDED = "points_needed"
        private const val POINTS_AWARDED = "points_awarded"
        private const val WASHER = "washer"
        private const val POLISHER = "polisher"
        private const val USER = "user"
        private const val POINTS = "points"
        private const val CAR_ID = "car_id"
        private const val DATE = "date"
        private const val BRANCH_NAME = "branch_name"
        private const val PRODUCT_NAME = "product_name"
        private const val AMOUNT = "amount"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // DO NO CHANGE THE CREATION ORDER
        createProviderTable(db) // #1
        createInputProductTable(db) // #2
        createWorkerTable(db) // #3
        createBranchOfficeTable(db) //#4
        createWashTable(db) // #5
        createClientTable(db) // #6
        createAppointmentTable(db) // #7
        createWorkerBranchTable(db) // #8
        createProviderInputTable(db) // #9
        createInputProductWashTable(db) // #10
        createClientPhoneTable(db) // #11
        createClientAddressTable(db) // #12
        createAppointmentWorkerTable(db) // #12+1
        createAppointmentInputTable(db) // #14
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_PROVIDER")
        db.execSQL("DROP TABLE IF EXISTS $TBL_INPUT_PRODUCT")
        db.execSQL("DROP TABLE IF EXISTS $TBL_WORKER")
        db.execSQL("DROP TABLE IF EXISTS $TBL_BRANCH_OFFICE")
        db.execSQL("DROP TABLE IF EXISTS $TBL_WASH")
        db.execSQL("DROP TABLE IF EXISTS $TBL_CLIENT")
        db.execSQL("DROP TABLE IF EXISTS $TBL_APPOINTMENT")
        db.execSQL("DROP TABLE IF EXISTS $TBL_WORKER_BRANCH")
        db.execSQL("DROP TABLE IF EXISTS $TBL_PROVIDER_INPUT")
        db.execSQL("DROP TABLE IF EXISTS $TBL_INPUT_PRODUCT_WASH")
        db.execSQL("DROP TABLE IF EXISTS $TBL_CLIENT_PHONE")
        db.execSQL("DROP TABLE IF EXISTS $TBL_CLIENT_ADDRESS")
        db.execSQL("DROP TABLE IF EXISTS $TBL_APPOINTMENT_WORKER")
        db.execSQL("DROP TABLE IF EXISTS $TBL_APPOINTMENT_INPUT")

        onCreate(db)
    }

    // Start of Provider Table methods

    private fun createProviderTable(db: SQLiteDatabase?) {
        val providerTable = ("CREATE TABLE " + TBL_PROVIDER + " (" +
                LEGAL_IDENTITY + " INTEGER PRIMARY KEY," +
                NAME + " TEXT," +
                EMAIL + " TEXT," +
                ADDRESS + " TEXT," +
                CONTACT + " TEXT" + ")")

        db?.execSQL(providerTable)
    }

    fun insertProvider(provider: ProviderModel): Long {
        val contentValues = ContentValues()
        contentValues.put(LEGAL_IDENTITY, provider.legalIdentity)
        contentValues.put(NAME, provider.name)
        contentValues.put(EMAIL, provider.email)
        contentValues.put(ADDRESS, provider.address)
        contentValues.put(CONTACT, provider.contact)

        return insertOnDatabase(contentValues, TBL_PROVIDER)
    }

    fun getAllProviderTable(): ArrayList<ProviderModel> {
        val providersList: ArrayList<ProviderModel> = ArrayList()
        val cursor = getFromDatabase(TBL_PROVIDER)

        var legalIdentity: Int
        var name: String
        var email: String
        var address: String
        var contact: String

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    legalIdentity = cursor.getInt(cursor.getColumnIndex(LEGAL_IDENTITY))
                    name = cursor.getString(cursor.getColumnIndex(NAME))
                    email = cursor.getString(cursor.getColumnIndex(EMAIL))
                    address = cursor.getString(cursor.getColumnIndex(ADDRESS))
                    contact = cursor.getString(cursor.getColumnIndex(CONTACT))

                    val provider = ProviderModel(legalIdentity = legalIdentity, name = name, email = email, address = address, contact = contact)
                    providersList.add(provider)
                } while (cursor.moveToNext())
            }
        }

        return providersList
    }

    fun updateProvider(provider: ProviderModel): Int {
        val contentValues = ContentValues()

        contentValues.put(LEGAL_IDENTITY, provider.legalIdentity)
        contentValues.put(NAME, provider.name)
        contentValues.put(EMAIL, provider.email)
        contentValues.put(ADDRESS, provider.address)
        contentValues.put(CONTACT, provider.contact)

        return updateInDatabase(TBL_PROVIDER, contentValues, LEGAL_IDENTITY + "=" + provider.legalIdentity)
    }

    fun deleteProvider(legalIdentity: Int): Int {
        return deleteFromDatabase(TBL_PROVIDER, "legal_identity=$legalIdentity")
    }

    // End of Provider Table methods
    // #############################################################################################
    // Start of InputProduct Table methods

    private fun createInputProductTable(db: SQLiteDatabase?) {
        val inputProductTable = ("CREATE TABLE " + TBL_INPUT_PRODUCT + " (" +
                NAME + " TEXT," +
                BRAND + " TEXT," +
                PRICE + " INTEGER," +
                " PRIMARY KEY(" + NAME + "," + BRAND + "))")

        db?.execSQL(inputProductTable)
    }

    fun insertInputProduct(product: ProductModel): Long {
        val contentValues = ContentValues()
        contentValues.put(NAME, product.name)
        contentValues.put(BRAND, product.brand)
        contentValues.put(PRICE, product.price)

        return insertOnDatabase(contentValues, TBL_INPUT_PRODUCT)
    }

    fun getAllProducts(): ArrayList<ProductModel> {
        val productsList: ArrayList<ProductModel> = ArrayList()
        val cursor = getFromDatabase(TBL_INPUT_PRODUCT)

        var name: String
        var brand: String
        var price: Int

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    name = cursor.getString(cursor.getColumnIndex(NAME))
                    brand = cursor.getString(cursor.getColumnIndex(BRAND))
                    price = cursor.getInt(cursor.getColumnIndex(PRICE))

                    val product = ProductModel(name = name, brand = brand, price = price)
                    productsList.add(product)
                } while (cursor.moveToNext())
            }
        }

        return productsList
    }

    fun updateProduct(product: ProductModel): Int {
        val contentValues = ContentValues()

        contentValues.put(NAME, product.name)
        contentValues.put(BRAND, product.brand)
        contentValues.put(PRICE, product.price)

        return updateInDatabase(TBL_INPUT_PRODUCT, contentValues, "name=" + product.name + " AND brand=" + product.brand)
    }

    fun deleteProduct(name: String, brand: String): Int {
        return deleteFromDatabase(TBL_INPUT_PRODUCT, "name=$name AND brand=$brand")
    }

    // End of InputProduct Table methods
    // #############################################################################################
    // Start of Worker Table methods

    private fun createWorkerTable(db: SQLiteDatabase?) {
        val workerTable = ("CREATE TABLE " + TBL_WORKER + " (" +
                ID + " INTEGER PRIMARY KEY," +
                NAME + " TEXT," +
                L_NAME1 + " TEXT," +
                L_NAME2 + " TEXT," +
                BIRTHDATE + " TEXT," + // DATE
                AGE + " INTEGER," +
                ROL + " TEXT," +
                PAYMENT_METHOD + " TEXT," +
                PASSWORD + " TEXT," +
                START_DATE + " TEXT" + ")") // DATE

        db?.execSQL(workerTable)
    }

    fun insertWorker(worker: WorkerModel): Long {
        val contentValues = ContentValues()
        contentValues.put(ID, worker.id)
        contentValues.put(NAME, worker.name)
        contentValues.put(L_NAME1, worker.lastName1)
        contentValues.put(L_NAME2, worker.lastName2)
        contentValues.put(BIRTHDATE, worker.birthDate)
        contentValues.put(AGE, worker.age)
        contentValues.put(ROL, worker.rol)
        contentValues.put(PAYMENT_METHOD, worker.paymentMethod)
        contentValues.put(PASSWORD, worker.password)
        contentValues.put(START_DATE, worker.startDate)

        return insertOnDatabase(contentValues, TBL_WORKER)
    }

    fun getAllWorkers(): ArrayList<WorkerModel> {
        val workersList: ArrayList<WorkerModel> = ArrayList()
        val cursor = getFromDatabase(TBL_WORKER)

        var id: Int
        var name: String
        var lastName1: String
        var lastName2 : String
        var birthDate: String
        var age: Int
        var rol: String
        var paymentMethod : String
        var password: String
        var startDate: String

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex(ID))
                    name = cursor.getString(cursor.getColumnIndex(NAME))
                    lastName1 = cursor.getString(cursor.getColumnIndex(L_NAME1))
                    lastName2 = cursor.getString(cursor.getColumnIndex(L_NAME2))
                    birthDate = cursor.getString(cursor.getColumnIndex(BIRTHDATE))
                    age = cursor.getInt(cursor.getColumnIndex(AGE))
                    rol = cursor.getString(cursor.getColumnIndex(ROL))
                    paymentMethod = cursor.getString(cursor.getColumnIndex(PAYMENT_METHOD))
                    password = cursor.getString(cursor.getColumnIndex(PASSWORD))
                    startDate = cursor.getString(cursor.getColumnIndex(START_DATE))

                    val worker = WorkerModel(id = id, name = name, lastName1 = lastName1, lastName2 = lastName2,
                        birthDate = birthDate, age = age, rol = rol, paymentMethod = paymentMethod, password = password,
                        startDate = startDate)

                    workersList.add(worker)
                } while (cursor.moveToNext())
            }
        }

        return workersList
    }

    fun updateWorker(worker: WorkerModel): Int {
        val contentValues = ContentValues()

        contentValues.put(ID, worker.id)
        contentValues.put(NAME, worker.name)
        contentValues.put(L_NAME1, worker.lastName1)
        contentValues.put(L_NAME2, worker.lastName2)
        contentValues.put(BIRTHDATE, worker.birthDate)
        contentValues.put(AGE, worker.age)
        contentValues.put(ROL, worker.rol)
        contentValues.put(PAYMENT_METHOD, worker.paymentMethod)
        contentValues.put(PASSWORD, worker.password)
        contentValues.put(START_DATE, worker.startDate)

        return updateInDatabase(TBL_WORKER, contentValues, ID + "=" + worker.id)
    }

    fun deleteWorker(id: Int): Int {
        return deleteFromDatabase(TBL_WORKER, "id=$id")
    }

    // End of Worker Table methods
    // #############################################################################################
    // Start of BranchOffice Table methods

    private fun createBranchOfficeTable(db: SQLiteDatabase?) {
        val branchOfficeTable = ("CREATE TABLE " + TBL_BRANCH_OFFICE + " (" +
                NAME + " TEXT PRIMARY KEY," +
                PHONE + " TEXT," +
                PROVINCE + " TEXT," +
                CANTON + " TEXT," +
                DISTRICT + " TEXT," +
                OPEN_DATE + " TEXT" + ")") // DATE

        db?.execSQL(branchOfficeTable)
    }

    fun insertBranchOffice(branchOffice: BranchOfficeModel): Long {
        val contentValues = ContentValues()
        contentValues.put(NAME, branchOffice.name)
        contentValues.put(PHONE, branchOffice.phone)
        contentValues.put(PROVINCE, branchOffice.province)
        contentValues.put(CANTON, branchOffice.canton)
        contentValues.put(DISTRICT, branchOffice.district)
        contentValues.put(OPEN_DATE, branchOffice.openDate)

        return insertOnDatabase(contentValues, TBL_BRANCH_OFFICE)
    }

    fun getAllBranchOffices(): ArrayList<BranchOfficeModel> {
        val branchOfficesList: ArrayList<BranchOfficeModel> = ArrayList()
        val cursor = getFromDatabase(TBL_BRANCH_OFFICE)

        var name: String
        var phone: String
        var province: String
        var canton: String
        var district: String
        var openDate: String

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    name = cursor.getString(cursor.getColumnIndex(NAME))
                    phone = cursor.getString(cursor.getColumnIndex(PHONE))
                    province = cursor.getString(cursor.getColumnIndex(PROVINCE))
                    canton = cursor.getString(cursor.getColumnIndex(CANTON))
                    district = cursor.getString(cursor.getColumnIndex(DISTRICT))
                    openDate = cursor.getString(cursor.getColumnIndex(OPEN_DATE))

                    val branchOffice = BranchOfficeModel(name = name, phone = phone, province = province,
                        canton = canton, district = district, openDate = openDate)
                    branchOfficesList.add(branchOffice)
                } while (cursor.moveToNext())
            }
        }

        return branchOfficesList
    }

    fun updateBranchOffice(branchOffice: BranchOfficeModel): Int {
        val contentValues = ContentValues()

        contentValues.put(NAME, branchOffice.name)
        contentValues.put(PHONE, branchOffice.phone)
        contentValues.put(PROVINCE, branchOffice.province)
        contentValues.put(CANTON, branchOffice.canton)
        contentValues.put(DISTRICT, branchOffice.district)
        contentValues.put(OPEN_DATE, branchOffice.openDate)

        return updateInDatabase(TBL_BRANCH_OFFICE, contentValues, NAME + "=" + branchOffice.name)
    }

    fun deleteBranchOffice(name: String): Int {
        return deleteFromDatabase(TBL_BRANCH_OFFICE, "name=$name")
    }

    // End of BranchOffice Table methods
    // #############################################################################################
    // Start of Wash Table methods

    private fun createWashTable(db: SQLiteDatabase?) {
        val washTable = ("CREATE TABLE " + TBL_WASH + " (" +
                TYPE + " TEXT PRIMARY KEY," +
                PRICE + " INTEGER," +
                ESTIMATED_DURATION + " INTEGER," +
                POINTS_NEEDED + " INTEGER," +
                POINTS_AWARDED + " INTEGER," +
                WASHER + " INTEGER," + // BIT
                POLISHER + " INTEGER" + ")") // BIT

        db?.execSQL(washTable)
    }

    fun insertWash(wash: WashModel): Long {
        val contentValues = ContentValues()
        contentValues.put(TYPE, wash.type)
        contentValues.put(PRICE, wash.price)
        contentValues.put(ESTIMATED_DURATION, wash.estimatedDuration)
        contentValues.put(POINTS_NEEDED, wash.pointsNeeded)
        contentValues.put(POINTS_AWARDED, wash.pointsAwarded)
        contentValues.put(WASHER, wash.washer)
        contentValues.put(POLISHER, wash.polisher)

        return insertOnDatabase(contentValues, TBL_WASH)
    }

    fun getAllWashes(): ArrayList<WashModel> {
        val washesList: ArrayList<WashModel> = ArrayList()
        val cursor = getFromDatabase(TBL_WASH)

        var type: String
        var price: Int
        var estimatedDuration: Int
        var pointsNeeded: Int
        var pointsAwarded: Int
        var washer: Int
        var polisher: Int

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    type = cursor.getString(cursor.getColumnIndex(TYPE))
                    price = cursor.getInt(cursor.getColumnIndex(PRICE))
                    estimatedDuration = cursor.getInt(cursor.getColumnIndex(ESTIMATED_DURATION))
                    pointsNeeded = cursor.getInt(cursor.getColumnIndex(POINTS_NEEDED))
                    pointsAwarded = cursor.getInt(cursor.getColumnIndex(POINTS_AWARDED))
                    washer = cursor.getInt(cursor.getColumnIndex(WASHER))
                    polisher = cursor.getInt(cursor.getColumnIndex(POLISHER))

                    val wash = WashModel(type = type, price = price, estimatedDuration = estimatedDuration,
                        pointsNeeded = pointsNeeded, pointsAwarded = pointsAwarded,
                        washer = washer, polisher = polisher)
                    washesList.add(wash)
                } while (cursor.moveToNext())
            }
        }

        return washesList
    }

    fun updateWash(wash: WashModel): Int {
        val contentValues = ContentValues()

        contentValues.put(TYPE, wash.type)
        contentValues.put(PRICE, wash.price)
        contentValues.put(ESTIMATED_DURATION, wash.estimatedDuration)
        contentValues.put(POINTS_NEEDED, wash.pointsNeeded)
        contentValues.put(POINTS_AWARDED, wash.pointsAwarded)
        contentValues.put(WASHER, wash.washer)
        contentValues.put(POLISHER, wash.polisher)

        return updateInDatabase(TBL_WASH, contentValues, TYPE + "=" + wash.type)
    }

    fun deleteWash(type: String): Int {
        return deleteFromDatabase(TBL_WASH, "type=$type")
    }

    // End of Wash Table methods
    // #############################################################################################
    // Start of Client Table methods

    private fun createClientTable(db: SQLiteDatabase?) {
        val clientTable = ("CREATE TABLE " + TBL_CLIENT + " (" +
                ID + " INTEGER PRIMARY KEY," +
                NAME + " TEXT," +
                L_NAME1 + " TEXT," +
                L_NAME2 + " TEXT," +
                USER + " TEXT," +
                PASSWORD + " TEXT," +
                EMAIL + " TEXT," +
                POINTS + " INTEGER" + ")")

        db?.execSQL(clientTable)
    }

    fun insertClient(client: ClientModel): Long {
        val contentValues = ContentValues()
        contentValues.put(ID, client.id)
        contentValues.put(NAME, client.name)
        contentValues.put(L_NAME1, client.lastName1)
        contentValues.put(L_NAME2, client.lastName2)
        contentValues.put(USER, client.user)
        contentValues.put(PASSWORD, client.password)
        contentValues.put(EMAIL, client.email)
        contentValues.put(POINTS, client.points)

        return insertOnDatabase(contentValues, TBL_CLIENT)
    }

    fun getAllClients(): ArrayList<ClientModel> {
        val clientsList: ArrayList<ClientModel> = ArrayList()
        val cursor = getFromDatabase(TBL_CLIENT)

        var id: Int
        var name: String
        var lastName1: String
        var lastName2: String
        var user: String
        var password: String
        var email: String
        var points: Int

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex(ID))
                    name = cursor.getString(cursor.getColumnIndex(NAME))
                    lastName1 = cursor.getString(cursor.getColumnIndex(L_NAME1))
                    lastName2 = cursor.getString(cursor.getColumnIndex(L_NAME2))
                    user = cursor.getString(cursor.getColumnIndex(USER))
                    password = cursor.getString(cursor.getColumnIndex(PASSWORD))
                    email = cursor.getString(cursor.getColumnIndex(EMAIL))
                    points = cursor.getInt(cursor.getColumnIndex(POINTS))

                    val client = ClientModel(
                        id = id, name = name, lastName1 = lastName1, lastName2 = lastName2,
                        user = user, password = password, email = email, points = points
                    )
                    clientsList.add(client)
                } while (cursor.moveToNext())
            }
        }

        return clientsList
    }

    fun updateClient(client: ClientModel): Int {
        val contentValues = ContentValues()

        contentValues.put(ID, client.id)
        contentValues.put(NAME, client.name)
        contentValues.put(L_NAME1, client.lastName1)
        contentValues.put(L_NAME2, client.lastName2)
        contentValues.put(USER, client.user)
        contentValues.put(PASSWORD, client.password)
        contentValues.put(EMAIL, client.email)
        contentValues.put(POINTS, client.points)

        return updateInDatabase(TBL_CLIENT, contentValues, ID + "=" + client.id)
    }

    fun deleteClient(id: Int): Int {
        return deleteFromDatabase(TBL_CLIENT, "id=$id")
    }

    // End of Client Table methods
    // #############################################################################################
    // Start of Appointment Table methods

    private fun createAppointmentTable(db: SQLiteDatabase?) {
        val appointmentTable = ("CREATE TABLE " + TBL_APPOINTMENT + " (" +
                CAR_ID + " INTEGER," +
                DATE + " TEXT," + // DATETIME
                TYPE + " TEXT," +
                NAME + " TEXT," +
                ID + " INTEGER," +
                BRANCH_NAME + " TEXT," +
                " PRIMARY KEY (" + CAR_ID + "," + DATE + "," + BRANCH_NAME + ")," +
                " FOREIGN KEY (" + TYPE + ") REFERENCES " + TBL_WASH + "(" + TYPE + ")," +
                " FOREIGN KEY (" + NAME + ") REFERENCES " + TBL_CLIENT + "(" + NAME + ")," +
                " FOREIGN KEY (" + ID + ") REFERENCES " + TBL_CLIENT + "(" + ID + ")," +
                " FOREIGN KEY (" + BRANCH_NAME + ") REFERENCES" + TBL_BRANCH_OFFICE + "(" + NAME + "))")

        db?.execSQL(appointmentTable)

    }

    fun insertAppointment(appointment: AppointmentModel): Long {
        val contentValues = ContentValues()
        contentValues.put(CAR_ID, appointment.carId)
        contentValues.put(DATE, appointment.date)
        contentValues.put(TYPE, appointment.type)
        contentValues.put(NAME, appointment.name)
        contentValues.put(ID, appointment.id)
        contentValues.put(BRANCH_NAME, appointment.branchName)

        return insertOnDatabase(contentValues, TBL_APPOINTMENT)
    }

    fun getAllAppointments(): ArrayList<AppointmentModel> {
        val appointmentsList: ArrayList<AppointmentModel> = ArrayList()
        val cursor = getFromDatabase(TBL_APPOINTMENT)

        var carId: Int
        var date: String
        var type: String
        var name: String
        var id: Int
        var branchName: String

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    carId = cursor.getInt(cursor.getColumnIndex(CAR_ID))
                    date = cursor.getString(cursor.getColumnIndex(DATE))
                    type = cursor.getString(cursor.getColumnIndex(TYPE))
                    name = cursor.getString(cursor.getColumnIndex(NAME))
                    id = cursor.getInt(cursor.getColumnIndex(ID))
                    branchName = cursor.getString(cursor.getColumnIndex(BRANCH_NAME))

                    val appointment = AppointmentModel(carId = carId, date = date, type = type,
                        name = name, id = id, branchName = branchName)
                    appointmentsList.add(appointment)
                } while (cursor.moveToNext())
            }
        }

        return appointmentsList
    }

    fun updateAppointment(appointment: AppointmentModel): Int {
        val contentValues = ContentValues()

        contentValues.put(CAR_ID, appointment.carId)
        contentValues.put(DATE, appointment.date)
        contentValues.put(TYPE, appointment.type)
        contentValues.put(NAME, appointment.name)
        contentValues.put(ID, appointment.id)
        contentValues.put(BRANCH_NAME, appointment.branchName)

        return updateInDatabase(TBL_APPOINTMENT, contentValues,
            CAR_ID + "=" + appointment.carId + " AND " + DATE + "=" + appointment.date + " AND " + BRANCH_NAME + "=" + appointment.branchName)
    }

    fun deleteAppointment(carId: Int, date: String, branchName: String): Int {
        return deleteFromDatabase(TBL_APPOINTMENT, "car_id=$carId AND date=$date AND branch_name=$branchName")
    }

    // End of Appointment Table methods
    // #############################################################################################
    // Start of WorkerBranch Table methods

    private fun createWorkerBranchTable(db: SQLiteDatabase?) {
        val workerBranch = ("CREATE TABLE " + TBL_WORKER_BRANCH + " (" +
                ID + " INTEGER," +
                NAME + " TEXT," +
                START_DATE + " TEXT," + // DATE
                " FOREIGN KEY (" + ID + ") REFERENCES " + TBL_WORKER + "(" + ID + ")," +
                " FOREIGN KEY (" + NAME + ") REFERENCES " + TBL_BRANCH_OFFICE + "(" + NAME + "))")

        db?.execSQL(workerBranch)
    }

    fun insertWorkerBranch(workerBranch: WorkerBranchModel): Long {
        val contentValues = ContentValues()
        contentValues.put(ID, workerBranch.id)
        contentValues.put(NAME, workerBranch.name)
        contentValues.put(START_DATE, workerBranch.startDate)

        return insertOnDatabase(contentValues, TBL_WORKER_BRANCH)
    }

    fun getAllWorkerBranches(): ArrayList<WorkerBranchModel> {
        val workerBranchesList: ArrayList<WorkerBranchModel> = ArrayList()
        val cursor = getFromDatabase(TBL_WORKER_BRANCH)

        var id: Int
        var name: String
        var startDate: String

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex(ID))
                    name = cursor.getString(cursor.getColumnIndex(NAME))
                    startDate = cursor.getString(cursor.getColumnIndex(START_DATE))

                    val workerBranch = WorkerBranchModel(id = id, name = name, startDate = startDate)
                    workerBranchesList.add(workerBranch)
                } while (cursor.moveToNext())
            }
        }

        return workerBranchesList
    }

    fun updateWorkerBranch(workerBranch: WorkerBranchModel): Int {
        val contentValues = ContentValues()

        contentValues.put(ID, workerBranch.id)
        contentValues.put(NAME, workerBranch.name)
        contentValues.put(START_DATE, workerBranch.startDate)

        return updateInDatabase(TBL_WORKER_BRANCH, contentValues, ID + "=" + workerBranch.id)
    }

    fun deleteWorkerBranch(id: Int): Int {
        return deleteFromDatabase(TBL_WORKER_BRANCH, "id=$id")
    }

    // End of WorkerBranch Table methods
    // #############################################################################################
    // Start of ProviderInput Table methods

    private fun createProviderInputTable(db: SQLiteDatabase?) {
        val providerInputTable = ("CREATE TABLE " + TBL_PROVIDER_INPUT + " (" +
                LEGAL_IDENTITY + " INTEGER," +
                NAME + " TEXT," +
                " FOREIGN KEY (" + LEGAL_IDENTITY + ") REFERENCES " + TBL_PROVIDER + "(" + LEGAL_IDENTITY + ")," +
                " FOREIGN KEY (" + NAME + ") REFERENCES " + TBL_INPUT_PRODUCT + "(" + NAME + "))")

        db?.execSQL(providerInputTable)
    }

    fun insertProviderInput(providerInput: ProviderInputModel): Long {
        val contentValues = ContentValues()
        contentValues.put(LEGAL_IDENTITY, providerInput.legalIdentity)
        contentValues.put(NAME, providerInput.name)

        return insertOnDatabase(contentValues, TBL_PROVIDER_INPUT)
    }

    fun getAllProviderInputs(): ArrayList<ProviderInputModel> {
        val providerInputsList: ArrayList<ProviderInputModel> = ArrayList()
        val cursor = getFromDatabase(TBL_PROVIDER_INPUT)

        var legalIdentity: Int
        var name: String

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    legalIdentity = cursor.getInt(cursor.getColumnIndex(LEGAL_IDENTITY))
                    name = cursor.getString(cursor.getColumnIndex(NAME))

                    val providerInput = ProviderInputModel(legalIdentity = legalIdentity, name = name)
                    providerInputsList.add(providerInput)
                } while (cursor.moveToNext())
            }
        }

        return providerInputsList
    }

    fun updateProviderInput(providerInput: ProviderInputModel): Int {
        val contentValues = ContentValues()

        contentValues.put(LEGAL_IDENTITY, providerInput.legalIdentity)
        contentValues.put(NAME, providerInput.name)

        return updateInDatabase(TBL_PROVIDER_INPUT, contentValues, LEGAL_IDENTITY + "=" + providerInput.legalIdentity)
    }

    fun deleteProviderInput(legalIdentity: Int): Int {
        return deleteFromDatabase(TBL_PROVIDER_INPUT, "legal_identity=$legalIdentity")
    }

    // End of ProviderInput Table methods
    // #############################################################################################
    // Start of InputProductWash Table methods

    private fun createInputProductWashTable(db: SQLiteDatabase?) {
        val inputProductWashTable = ("CREATE TABLE " + TBL_INPUT_PRODUCT_WASH + " (" +
                NAME + " TEXT," +
                TYPE + " TEXT," +
                BRAND + " TEXT," +
                " FOREIGN KEY (" + NAME + ") REFERENCES " + TBL_INPUT_PRODUCT + "(" + NAME + ")," +
                " FOREIGN KEY (" + TYPE + ") REFERENCES " + TBL_WASH + "(" + TYPE + ")," +
                " FOREIGN KEY (" + BRAND + ") REFERENCES " + TBL_INPUT_PRODUCT + "(" + BRAND + "))")

        db?.execSQL(inputProductWashTable)
    }

    fun insertInputProductWash(inputProductWash: InputProductWashModel): Long {
        val contentValues = ContentValues()
        contentValues.put(NAME, inputProductWash.name)
        contentValues.put(TYPE, inputProductWash.type)
        contentValues.put(BRAND, inputProductWash.brand)

        return insertOnDatabase(contentValues, TBL_INPUT_PRODUCT_WASH)
    }

    fun getAllInputProductWashes(): ArrayList<InputProductWashModel> {
        val inputProductWashesList: ArrayList<InputProductWashModel> = ArrayList()
        val cursor = getFromDatabase(TBL_INPUT_PRODUCT_WASH)

        var name: String
        var type: String
        var brand: String

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    name = cursor.getString(cursor.getColumnIndex(NAME))
                    type = cursor.getString(cursor.getColumnIndex(TYPE))
                    brand = cursor.getString(cursor.getColumnIndex(BRAND))

                    val inputProductWash = InputProductWashModel(name = name, type = type, brand = brand)
                    inputProductWashesList.add(inputProductWash)
                } while (cursor.moveToNext())
            }
        }

        return inputProductWashesList
    }

    fun updateInputProductWash(inputProductWash: InputProductWashModel): Int {
        val contentValues = ContentValues()

        contentValues.put(NAME, inputProductWash.name)
        contentValues.put(TYPE, inputProductWash.type)
        contentValues.put(BRAND, inputProductWash.brand)

        return updateInDatabase(TBL_INPUT_PRODUCT_WASH, contentValues, NAME + "=" + inputProductWash.name)
    }

    fun deleteInputProductWash(name: String): Int {
        return deleteFromDatabase(TBL_INPUT_PRODUCT_WASH, "name=$name")
    }

    // End of InputProductWash Table methods
    // #############################################################################################
    // Start of ClientPhone Table methods

    private fun createClientPhoneTable(db: SQLiteDatabase?) {
        val clientPhoneTable = ("CREATE TABLE " + TBL_CLIENT_PHONE + " (" +
                ID + " INTEGER," +
                PHONE + " TEXT," +
                " FOREIGN KEY (" + ID + ") REFERENCES " + TBL_CLIENT + "(" + ID + "))")

        db?.execSQL(clientPhoneTable)
    }

    fun insertClientPhone(clientPhone: ClientPhoneModel): Long {
        val contentValues = ContentValues()
        contentValues.put(ID, clientPhone.id)
        contentValues.put(PHONE, clientPhone.phone)

        return insertOnDatabase(contentValues, TBL_CLIENT_PHONE)
    }

    fun getAllClientPhones(): ArrayList<ClientPhoneModel> {
        val clientPhonesList: ArrayList<ClientPhoneModel> = ArrayList()
        val cursor = getFromDatabase(TBL_CLIENT_PHONE)

        var id: Int
        var phone: String

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex(ID))
                    phone = cursor.getString(cursor.getColumnIndex(PHONE))

                    val clientPhone = ClientPhoneModel(id = id, phone = phone)
                    clientPhonesList.add(clientPhone)
                } while (cursor.moveToNext())
            }
        }

        return clientPhonesList
    }

    fun updateClientPhone(clientPhone: ClientPhoneModel): Int {
        val contentValues = ContentValues()

        contentValues.put(ID, clientPhone.id)
        contentValues.put(PHONE, clientPhone.phone)

        return updateInDatabase(TBL_CLIENT_PHONE, contentValues, ID + "=" + clientPhone.id)
    }

    fun deleteClientPhone(id: Int): Int {
        return deleteFromDatabase(TBL_CLIENT_PHONE, "id=$id")
    }

    // End of ClientPhone Table methods
    // #############################################################################################
    // Start of ClientAddress Table methods

    private fun createClientAddressTable(db: SQLiteDatabase?) {
        val clientAddressTable = ("CREATE TABLE " + TBL_CLIENT_ADDRESS + " (" +
                ID + " INTEGER," +
                ADDRESS + " TEXT," +
                " FOREIGN KEY (" + ID + ") REFERENCES " + TBL_CLIENT + "(" + ID + "))")

        db?.execSQL(clientAddressTable)
    }

    fun insertClientAddress(clientAddress: ClientAddressModel): Long {
        val contentValues = ContentValues()
        contentValues.put(ID, clientAddress.id)
        contentValues.put(ADDRESS, clientAddress.address)

        return insertOnDatabase(contentValues, TBL_CLIENT_ADDRESS)
    }

    fun getAllClientAddresses(): ArrayList<ClientAddressModel> {
        val clientAddressesList: ArrayList<ClientAddressModel> = ArrayList()
        val cursor = getFromDatabase(TBL_CLIENT_ADDRESS)

        var id: Int
        var address: String

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex(ID))
                    address = cursor.getString(cursor.getColumnIndex(ADDRESS))

                    val clientAddress = ClientAddressModel(id = id, address = address)
                    clientAddressesList.add(clientAddress)
                } while (cursor.moveToNext())
            }
        }

        return clientAddressesList
    }

    fun updateClientAddress(clientAddress: ClientAddressModel): Int {
        val contentValues = ContentValues()

        contentValues.put(ID, clientAddress.id)
        contentValues.put(ADDRESS, clientAddress.address)

        return updateInDatabase(TBL_CLIENT_ADDRESS, contentValues, ID + "=" + clientAddress.id)
    }

    fun deleteClientAddress(id: Int): Int {
        return deleteFromDatabase(TBL_CLIENT_ADDRESS, "id=$id")
    }

    // End of ClientAddress Table methods
    // #############################################################################################
    // Start of AppointmentWorker Table methods

    private fun createAppointmentWorkerTable(db: SQLiteDatabase?) {
        val appointmentWorkerTable = ("CREATE TABLE " + TBL_APPOINTMENT_WORKER + " (" +
                CAR_ID + " INTEGER," +
                DATE + " TEXT," + // DATETIME
                BRANCH_NAME + " TEXT," +
                ID + " INTEGER," +
                " FOREIGN KEY (" + CAR_ID + ") REFERENCES " + TBL_APPOINTMENT + "(" + CAR_ID + ")," +
                " FOREIGN KEY (" + DATE + ") REFERENCES " + TBL_APPOINTMENT + "(" + DATE + ")," +
                " FOREIGN KEY (" + BRANCH_NAME + ") REFERENCES " + TBL_BRANCH_OFFICE + "(" + NAME + ")," +
                " FOREIGN KEY (" + ID + ") REFERENCES " + TBL_WORKER + "(" + ID + "))")

        db?.execSQL(appointmentWorkerTable)
    }

    fun insertAppointmentWorker(appointmentWorker: AppointmentWorkerModel): Long {
        val contentValues = ContentValues()
        contentValues.put(CAR_ID, appointmentWorker.carId)
        contentValues.put(DATE, appointmentWorker.date)
        contentValues.put(BRANCH_NAME, appointmentWorker.branchName)
        contentValues.put(ID, appointmentWorker.id)

        return insertOnDatabase(contentValues, TBL_APPOINTMENT_WORKER)
    }

    fun getAllAppointmentWorkers(): ArrayList<AppointmentWorkerModel> {
        val appointmentWorkersList: ArrayList<AppointmentWorkerModel> = ArrayList()
        val cursor = getFromDatabase(TBL_APPOINTMENT_WORKER)

        var carId: Int
        var date: String
        var branchName: String
        var id: Int

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    carId = cursor.getInt(cursor.getColumnIndex(CAR_ID))
                    date = cursor.getString(cursor.getColumnIndex(DATE))
                    branchName = cursor.getString(cursor.getColumnIndex(BRANCH_NAME))
                    id = cursor.getInt(cursor.getColumnIndex(ID))

                    val appointmentWorker = AppointmentWorkerModel(carId = carId, date = date, branchName = branchName, id = id)
                    appointmentWorkersList.add(appointmentWorker)
                } while (cursor.moveToNext())
            }
        }

        return appointmentWorkersList
    }

    fun updateAppointmentWorker(appointmentWorker: AppointmentWorkerModel): Int {
        val contentValues = ContentValues()

        contentValues.put(CAR_ID, appointmentWorker.carId)
        contentValues.put(DATE, appointmentWorker.date)
        contentValues.put(BRANCH_NAME, appointmentWorker.branchName)
        contentValues.put(ID, appointmentWorker.id)

        return updateInDatabase(TBL_APPOINTMENT_WORKER, contentValues, CAR_ID + "=" + appointmentWorker.carId)
    }

    fun deleteAppointmentWorker(carId: Int): Int {
        return deleteFromDatabase(TBL_APPOINTMENT_WORKER, "car_id=$carId")
    }

    // End of AppointmentWorker Table methods
    // #############################################################################################
    // Start of AppointmentInput Table methods

    private fun createAppointmentInputTable(db: SQLiteDatabase?) {
        val appointmentInputTable = ("CREATE TABLE " + TBL_APPOINTMENT_INPUT + " (" +
                CAR_ID + " INTEGER," +
                DATE + " TEXT," + // DATETIME
                PRODUCT_NAME + " TEXT," +
                BRAND + " TEXT," +
                AMOUNT + " INTEGER," +
                BRANCH_NAME + " TEXT," +
                " FOREIGN KEY (" + CAR_ID + ") REFERENCES " + TBL_APPOINTMENT + "(" + CAR_ID + ")," +
                " FOREIGN KEY (" + DATE + ") REFERENCES " + TBL_APPOINTMENT + "(" + DATE + ")," +
                " FOREIGN KEY (" + PRODUCT_NAME + ") REFERENCES " + TBL_INPUT_PRODUCT + "(" + NAME + ")," +
                " FOREIGN KEY (" + BRAND + ") REFERENCES " + TBL_INPUT_PRODUCT + "(" + BRAND + ")," +
                " FOREIGN KEY (" + BRANCH_NAME + ") REFERENCES " + TBL_BRANCH_OFFICE + "(" + NAME + "))")

        db?.execSQL(appointmentInputTable)
    }

    fun insertAppointmentInput(appointmentInput: AppointmentInputModel): Long {
        val contentValues = ContentValues()
        contentValues.put(CAR_ID, appointmentInput.carId)
        contentValues.put(DATE, appointmentInput.date)
        contentValues.put(PRODUCT_NAME, appointmentInput.productName)
        contentValues.put(BRAND, appointmentInput.brand)
        contentValues.put(AMOUNT, appointmentInput.amount)
        contentValues.put(BRANCH_NAME, appointmentInput.branchName)

        return insertOnDatabase(contentValues, TBL_APPOINTMENT_INPUT)
    }

    fun getAllAppointmentInputs(): ArrayList<AppointmentInputModel> {
        val appointmentInputsList: ArrayList<AppointmentInputModel> = ArrayList()
        val cursor = getFromDatabase(TBL_APPOINTMENT_INPUT)

        var carId: Int
        var date: String
        var productName: String
        var brand: String
        var amount: Int
        var branchName: String

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    carId = cursor.getInt(cursor.getColumnIndex(CAR_ID))
                    date = cursor.getString(cursor.getColumnIndex(DATE))
                    productName = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME))
                    brand = cursor.getString(cursor.getColumnIndex(BRAND))
                    amount = cursor.getInt(cursor.getColumnIndex(AMOUNT))
                    branchName = cursor.getString(cursor.getColumnIndex(BRANCH_NAME))

                    val appointmentInput = AppointmentInputModel(carId = carId, date = date, productName = productName,
                        brand = brand, amount = amount, branchName = branchName)

                    appointmentInputsList.add(appointmentInput)
                } while (cursor.moveToNext())
            }
        }

        return appointmentInputsList
    }

    fun updateAppointmentInput(appointmentInput: AppointmentInputModel): Int {
        val contentValues = ContentValues()

        contentValues.put(CAR_ID, appointmentInput.carId)
        contentValues.put(DATE, appointmentInput.date)
        contentValues.put(PRODUCT_NAME, appointmentInput.productName)
        contentValues.put(BRAND, appointmentInput.brand)
        contentValues.put(AMOUNT, appointmentInput.amount)
        contentValues.put(BRANCH_NAME, appointmentInput.branchName)

        return updateInDatabase(TBL_APPOINTMENT_INPUT, contentValues, CAR_ID + "=" + appointmentInput.carId)
    }

    fun deleteAppointmentInput(carId: Int): Int {
        return deleteFromDatabase(TBL_APPOINTMENT_INPUT, "car_id=$carId")
    }

    // End of AppointmentInput Table methods
    // #############################################################################################

    private fun insertOnDatabase(contentValues: ContentValues, tableName: String): Long {
        val db = this.writableDatabase
        val success = db.insert(tableName, null, contentValues)
        db.close()

        return success
    }

    private fun getFromDatabase(tableName: String): Cursor? {
        val selectQuery = "SELECT * FROM $tableName"
        val db = this.readableDatabase

        val cursor: Cursor

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return null
        }

        return cursor
    }

    private fun updateInDatabase(tableName: String, contentValues: ContentValues, whereClause: String): Int {
        val db = writableDatabase
        val success = db.update(tableName, contentValues, whereClause, null)
        db.close()

        return success
    }

    private fun deleteFromDatabase(tableName: String, whereClause: String): Int {
        val db = this.writableDatabase
        val success = db.delete(tableName, whereClause, null)
        db.close()

        return success
    }
}