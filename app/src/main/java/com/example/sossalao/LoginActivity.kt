package com.example.sossalao

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Ao clicar enter com o cursor no editText da senha executa o login
        password.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                onClickLogin()
            }
            false
        })
        loginBtn.setOnClickListener {onClickLogin() }
        change_password.setOnClickListener { onChangePassword() }
    }

    private fun onClickLogin(){
        val intent = Intent(this, HomeActivity::class.java)
        val login = username.text.toString()
        val password = password.text.toString()
        if (login.length > 3 && password.length > 3) {
            val getToken = loginAuthenticator(login, password)
            if (getToken.length > 500) {
                Toast.makeText(this, "Bem-vindo", Toast.LENGTH_SHORT).show()
                intent.putExtra("tokenAuth", getToken);
                startActivity(intent)
            } else {
                Toast.makeText(this, "Login ou senha inválidos", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Necessario inserir username e senha!", Toast.LENGTH_SHORT).show()
        }
    }

    fun loginAuthenticator(userName: String, password: String?): String {
        val serverURL: String = "http://18.213.115.53:32768/api/auth"
        val url = URL(serverURL)
        var token = ""
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.connectTimeout = 300000
        connection.connectTimeout = 300000
        connection.doOutput = true
        val postData = "{\"user\": \"$userName\",\"password\": \"$password\"}";
        connection.setRequestProperty("charset", "utf-8")
        connection.setRequestProperty("Content-length", postData)
        connection.setRequestProperty("Content-Type", "application/json")
        try {
            val outputStream: DataOutputStream = DataOutputStream(connection.outputStream)
            outputStream.write(postData.toByteArray())
            outputStream.flush()
        } catch (exception: Exception) {
            throw Exception("Exception while pushing authentication  ${exception.message}")
        }
        if (connection.responseCode == 200) {
            val inputStream: DataInputStream = DataInputStream(connection.inputStream)
            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()
                val inputLine = it.readLine()
                response.append(inputLine)
                val obj = JSONObject(response.toString())
                obj.getString("accessToken").also { token = it }
            }
            return token
        }
        return "Falha ao autenticar: "+connection.responseCode
    }
    fun createPeople(name: String, phoneNumber: String, email: String, userName: String, password: String){
        val serverPeopleURL: String = "http://18.213.115.53:32768/api/people"
        val url = URL(serverPeopleURL)
        var idPeople = 0;
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.connectTimeout = 300000
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

    private fun onChangePassword(){
        val intent = Intent(this, ChangePassReqActivity::class.java)
        startActivity(intent)
    }
}