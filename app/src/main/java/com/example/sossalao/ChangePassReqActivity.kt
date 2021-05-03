package com.example.sossalao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_change_pass_req.*

class ChangePassReqActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass_req)

        username.setOnKeyListener(View.OnKeyListener{ v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN){
                onClickSendCode()
            }
            false
        })

        send_btn.setOnClickListener { onClickSendCode() }
    }

    private fun onClickSendCode(){
        val username = username.text.toString()

        if (username == "aluno"){
            sendCode()
        } else {
            Toast.makeText(this, "Usuário não cadastrado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendCode(){
        // lógica de envio de código
        val intent = Intent(this, RecoveryCodeActivity::class.java)
        Toast.makeText(this, "Código de recuperação enviado", Toast.LENGTH_SHORT).show()

        startActivity(intent)
        finish()
    }
}