package com.example.myapp

import android.os.StrictMode
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class ConnectSql {

    private val ip = "192.168.18.216:54335"
    private val db = "prueba"
    private val username = "sa"
    private val password = "12345678"

    fun dbCon(): Connection? {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var conn : Connection? = null
        val connString : String
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance()
            connString = "jdbc:jtds:sqlserver://192.168.18.216:54335;" + "databaseName=DetailTEC;user=sa;password=12345678;"

            conn = DriverManager.getConnection(connString)

            println("SE CONECTAAAAA")
        } catch (ex: SQLException){
            Log.e("Error: ", ex.message!!)
        } catch (ex1: ClassNotFoundException){
            Log.e("Error: ", ex1.message!!)
        } catch (ex2: Exception){
            Log.e("Error: ", ex2.message!!)
        }

        return conn

    }


}