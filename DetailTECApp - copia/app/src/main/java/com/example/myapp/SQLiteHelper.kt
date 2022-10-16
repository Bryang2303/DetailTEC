package com.example.myapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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

    private fun createProviderTable(db: SQLiteDatabase?) {
        val providerTable = ("CREATE TABLE " + TBL_PROVIDER + " (" +
                LEGAL_IDENTITY + " TEXT PRIMARY KEY," +
                NAME + " TEXT," +
                EMAIL + " TEXT," +
                ADDRESS + " TEXT," +
                CONTACT + " TEXT" + ")")

        db?.execSQL(providerTable)
    }

    private fun createInputProductTable(db: SQLiteDatabase?) {
        val inputProductTable = ("CREATE TABLE " + TBL_INPUT_PRODUCT + " (" +
                NAME + " TEXT," +
                BRAND + " TEXT," +
                PRICE + " TEXT," +
                " PRIMARY KEY(" + NAME + "," + BRAND + "))")

        db?.execSQL(inputProductTable)
    }

    private fun createWorkerTable(db: SQLiteDatabase?) {
        val workerTable = ("CREATE TABLE " + TBL_WORKER + " (" +
                ID + " TEXT PRIMARY KEY," +
                NAME + " TEXT," +
                L_NAME1 + " TEXT," +
                L_NAME2 + " TEXT," +
                BIRTHDATE + " TEXT," +
                AGE + " TEXT," +
                ROL + " TEXT," +
                PAYMENT_METHOD + " TEXT," +
                PASSWORD + " TEXT," +
                START_DATE + " TEXT" + ")")

        db?.execSQL(workerTable)
    }

    private fun createBranchOfficeTable(db: SQLiteDatabase?) {
        val branchOfficeTable = ("CREATE TABLE " + TBL_BRANCH_OFFICE + " (" +
                NAME + " TEXT PRIMARY KEY," +
                PHONE + " TEXT," +
                PROVINCE + " TEXT," +
                CANTON + " TEXT," +
                DISTRICT + " TEXT," +
                OPEN_DATE + " TEXT" + ")")

        db?.execSQL(branchOfficeTable)
    }

    private fun createWashTable(db: SQLiteDatabase?) {
        val washTable = ("CREATE TABLE " + TBL_WASH + " (" +
                TYPE + " TEXT PRIMARY KEY," +
                PRICE + " TEXT," +
                ESTIMATED_DURATION + " TEXT," +
                POINTS_NEEDED + " TEXT," +
                POINTS_AWARDED + " TEXT," +
                WASHER + " TEXT," +
                POLISHER + " TEXT" + ")")

        db?.execSQL(washTable)
    }

    private fun createClientTable(db: SQLiteDatabase?) {
        val clientTable = ("CREATE TABLE " + TBL_CLIENT + " (" +
                ID + " TEXT PRIMARY KEY," +
                NAME + " TEXT," +
                L_NAME1 + " TEXT," +
                L_NAME2 + " TEXT," +
                USER + " TEXT," +
                PASSWORD + " TEXT," +
                EMAIL + " TEXT," +
                POINTS + " TEXT" + ")")

        db?.execSQL(clientTable)
    }

    private fun createAppointmentTable(db: SQLiteDatabase?) {
        val appointmentTable = ("CREATE TABLE " + TBL_APPOINTMENT + " (" +
                CAR_ID + " TEXT," +
                DATE + " TEXT," +
                TYPE + " TEXT," +
                NAME + " TEXT," +
                ID + " TEXT," +
                BRANCH_NAME + " TEXT," +
                " PRIMARY KEY (" + CAR_ID + "," + DATE + ")," +
                " FOREIGN KEY (" + TYPE + ") REFERENCES " + TBL_WASH + "(" + TYPE + ")," +
                " FOREIGN KEY (" + NAME + ") REFERENCES " + TBL_CLIENT + "(" + NAME + ")," +
                " FOREIGN KEY (" + ID + ") REFERENCES " + TBL_CLIENT + "(" + ID + ")," +
                " FOREIGN KEY (" + BRANCH_NAME + ") REFERENCES" + TBL_BRANCH_OFFICE + "(" + NAME + "))")

        db?.execSQL(appointmentTable)

    }

    private fun createWorkerBranchTable(db: SQLiteDatabase?) {
        val workerBranch = ("CREATE TABLE " + TBL_WORKER_BRANCH + " (" +
                ID + " TEXT," +
                NAME + " TEXT," +
                START_DATE + " TEXT," +
                " FOREIGN KEY (" + ID + ") REFERENCES " + TBL_WORKER + "(" + ID + ")," +
                " FOREIGN KEY (" + NAME + ") REFERENCES " + TBL_BRANCH_OFFICE + "(" + NAME + "))")

        db?.execSQL(workerBranch)
    }

    private fun createProviderInputTable(db: SQLiteDatabase?) {
        val providerInputTable = ("CREATE TABLE " + TBL_PROVIDER_INPUT + " (" +
                LEGAL_IDENTITY + " TEXT," +
                NAME + " TEXT," +
                " FOREIGN KEY (" + LEGAL_IDENTITY + ") REFERENCES " + TBL_PROVIDER + "(" + LEGAL_IDENTITY + ")," +
                " FOREIGN KEY (" + NAME + ") REFERENCES " + TBL_INPUT_PRODUCT + "(" + NAME + "))")

        db?.execSQL(providerInputTable)
    }

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

    private fun createClientPhoneTable(db: SQLiteDatabase?) {
        val clientPhoneTable = ("CREATE TABLE " + TBL_CLIENT_PHONE + " (" +
                ID + " TEXT," +
                PHONE + " TEXT," +
                " FOREIGN KEY (" + ID + ") REFERENCES " + TBL_CLIENT + "(" + ID + "))")

        db?.execSQL(clientPhoneTable)
    }

    private fun createClientAddressTable(db: SQLiteDatabase?) {
        val clientAddressTable = ("CREATE TABLE " + TBL_CLIENT_ADDRESS + " (" +
                ID + " TEXT," +
                ADDRESS + " TEXT," +
                " FOREIGN KEY (" + ID + ") REFERENCES " + TBL_CLIENT + "(" + ID + "))")

        db?.execSQL(clientAddressTable)
    }

    private fun createAppointmentWorkerTable(db: SQLiteDatabase?) {
        val appointmentWorkerTable = ("CREATE TABLE " + TBL_APPOINTMENT_WORKER + " (" +
                CAR_ID + " TEXT," +
                DATE + " TEXT," +
                BRANCH_NAME + " TEXT," +
                ID + " TEXT," +
                " FOREIGN KEY (" + CAR_ID + ") REFERENCES " + TBL_APPOINTMENT + "(" + CAR_ID + ")," +
                " FOREIGN KEY (" + DATE + ") REFERENCES " + TBL_APPOINTMENT + "(" + DATE + ")," +
                " FOREIGN KEY (" + BRANCH_NAME + ") REFERENCES " + TBL_BRANCH_OFFICE + "(" + NAME + ")," +
                " FOREIGN KEY (" + ID + ") REFERENCES " + TBL_WORKER + "(" + ID + "))")

        db?.execSQL(appointmentWorkerTable)
    }

    private fun createAppointmentInputTable(db: SQLiteDatabase?) {
        val appointmentInputTable = ("CREATE TABLE " + TBL_APPOINTMENT_INPUT + " (" +
                CAR_ID + " TEXT," +
                DATE + " TEXT," +
                PRODUCT_NAME + " TEXT," +
                BRAND + " TEXT," +
                AMOUNT + " TEXT," +
                BRANCH_NAME + " TEXT," +
                " FOREIGN KEY (" + CAR_ID + ") REFERENCES " + TBL_APPOINTMENT + "(" + CAR_ID + ")," +
                " FOREIGN KEY (" + DATE + ") REFERENCES " + TBL_APPOINTMENT + "(" + DATE + ")," +
                " FOREIGN KEY (" + PRODUCT_NAME + ") REFERENCES " + TBL_INPUT_PRODUCT + "(" + NAME + ")," +
                " FOREIGN KEY (" + BRAND + ") REFERENCES " + TBL_INPUT_PRODUCT + "(" + BRAND + ")," +
                " FOREIGN KEY (" + BRANCH_NAME + ") REFERENCES " + TBL_BRANCH_OFFICE + "(" + NAME + "))")

        db?.execSQL(appointmentInputTable)
    }
}