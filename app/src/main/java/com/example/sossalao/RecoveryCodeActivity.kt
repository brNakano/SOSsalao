package com.example.sossalao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_recovery_code.*

class RecoveryCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery_code)

        recovery_code.setOnKeyListener(View.OnKeyListener{ v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN){
                AUTHChangePassword()
            }
            false
        })

        recovery_code_btn.setOnClickListener { sendRecoveryCode() }
        send_btn.setOnClickListener { AUTHChangePassword() }
    }

    private fun sendRecoveryCode(){
        // lógica para enviar o código de recuperação
        Toast.makeText(this, "Código de recuperação reenviado", Toast.LENGTH_SHORT).show()
    }

    private fun AUTHChangePassword(){
        val code = recovery_code.text.toString()

        if (code == "123"){
            val intent = Intent(this, ChangePassActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Código inválido", Toast.LENGTH_SHORT).show()
        }
    }
}