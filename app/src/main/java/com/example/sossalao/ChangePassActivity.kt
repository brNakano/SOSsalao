package com.example.sossalao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_change_pass.*

class ChangePassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)

        conf_password.setOnKeyListener(View.OnKeyListener{ v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN){
                checkPassword()
            }
            false
        })

        confirm_btn.setOnClickListener { checkPassword() }
    }

    private fun checkPassword(){
        val password1 = password.text.toString()
        val password2 = conf_password.text.toString()

        if (password1 == password2){
            val intent = Intent(this, LoginActivity::class.java)
            Toast.makeText(this, "Senha trocada com sucesso", Toast.LENGTH_SHORT).show()

            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Senhas não são iguais", Toast.LENGTH_SHORT).show()
        }
    }
}