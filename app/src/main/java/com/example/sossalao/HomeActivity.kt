package com.example.sossalao

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val it = intent
        val token = it.getStringExtra("tokenAuth")
        Log.i("Informacao: ", token.toString())
        action_add.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        Toast.makeText(this, token.toString(), Toast.LENGTH_LONG).show()
        bottom_home.replaceMenu(R.menu.menu)
        bottom_home.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_search -> Toast.makeText(this@HomeActivity, "Deseja pesquisar algo?", Toast.LENGTH_SHORT).show()
                R.id.action_config -> startActivity(Intent(this, SettingsActivity::class.java))
                R.id.action_refresh -> loading()
                R.id.action_logout -> Runtime.getRuntime().exit(0)
            };   true
        }
    }
    private fun loading(){
        GlobalScope.launch(context = Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            delay(10000)
            progressBar.visibility = View.GONE
        }
    }
}
