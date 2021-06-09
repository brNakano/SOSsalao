package com.example.sossalao.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sossalao.Prefs
import com.example.sossalao.R
import com.example.sossalao.ui.schedule.ScheduleActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    fun dev_pass(){
        val intent = Intent(this, ScheduleActivity::class.java)
        loginAuthenticator("hrqlp", "devsos21!")
        startActivity(intent)
    }

    private fun onClickLogin(){
        val intent = Intent(this, ScheduleActivity::class.java)
        val login = username.text.toString()
        val passwordCheck = password.text.toString()
        if (login.length > 3 && passwordCheck.length > 3) {
            if (loginAuthenticator(login, passwordCheck)) {
                Toast.makeText(this, "Bem-vindo", Toast.LENGTH_SHORT).show()
                startActivity(intent)

                // esconde a ação de limpar os campos
                GlobalScope.launch(context = Dispatchers.Main) {
                    delay(1000)
                    // Limpar campos do login após entrar
                    username.setText("")
                    password.setText("")
                }


            } else {
                Toast.makeText(this, "Login ou senha inválidos", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Necessario inserir username e senha!", Toast.LENGTH_SHORT).show()
        }
    }

    fun loginAuthenticator(userName: String, password: String?): Boolean {
        val serverURL: String = "http://3.93.42.204:32773/api/auth"
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
            Prefs.setString("API_TOKEN", token)
            return true
        }
        return false
    }
    private fun onChangePassword(){
        Toast.makeText(this, "Não é possivel trocar de senha", Toast.LENGTH_SHORT).show()
    }
}