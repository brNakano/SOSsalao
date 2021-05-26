package com.example.sossalao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class RegisterLoginPeople : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_login_people)
    }
    fun createPeople(name: String, phoneNumber: String, email: String, userName: String, password: String){
        val serverPeopleURL: String = "http://18.213.115.53:32768/api/people"
        val url = URL(serverPeopleURL)
        var idPeople = 0;
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.connectTimeout = 300000
        connection.doOutput = true
        val postPeopleData = "{\"name\": \"$name\",\"phoneNumber\": \"$phoneNumber\",\"email\": \"$email\",\"typePeople\": \"1\"}";
        connection.setRequestProperty("charset", "utf-8")
        connection.setRequestProperty("Content-length", postPeopleData)
        connection.setRequestProperty("Content-Type", "application/json")
        try {
            val outputStream: DataOutputStream = DataOutputStream(connection.outputStream)
            outputStream.write(postPeopleData.toByteArray())
            outputStream.flush()
        } catch (exception: Exception) {
            throw Exception("Exception while send data of people entity  ${exception.message}")
        }
        if (connection.responseCode == 200) {
            val inputStream: DataInputStream = DataInputStream(connection.inputStream)
            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()
                val inputLine = it.readLine()
                response.append(inputLine)
                val obj = JSONObject(response.toString())
                idPeople = obj.getInt("idPeople")//.also //{ idPeople = it }
                createLogin(userName, password, idPeople);
            }
            //    return idPeople
        }
        //  return connection.responseCode
    }
    private fun createLogin(user: String, password: String, peopleId: Int){
        val serverPeopleURL: String = "http://18.213.115.53:32768/api/login"
        val url = URL(serverPeopleURL)
        var idPeople = 0;
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.connectTimeout = 300000
        connection.doOutput = true
        val postLoginData = "{\"user\": \"$user\",\"password\": \"$password\",\"typeArea\": \"1\",\"typeEmployee\": \"2\",\"accessLevel\": \"0\",\"peopleId\": \"$peopleId\"}";
        connection.setRequestProperty("charset", "utf-8")
        connection.setRequestProperty("Content-length", postLoginData)
        connection.setRequestProperty("Content-Type", "application/json")
        try {
            val outputStream: DataOutputStream = DataOutputStream(connection.outputStream)
            outputStream.write(postLoginData.toByteArray())
            outputStream.flush()
        } catch (exception: Exception) {
            throw Exception("Exception while send data of login entity  ${exception.message}")
        }
        if (connection.responseCode == 200) {
            val inputStream: DataInputStream = DataInputStream(connection.inputStream)
            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()
                val inputLine = it.readLine()
                response.append(inputLine)
                val obj = JSONObject(response.toString())
                idPeople = obj.getInt("idPeople")//.also //{ idPeople = it }
            }
        }
    }
}