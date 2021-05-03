package com.example.sossalao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        action_add.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        bottom_home.replaceMenu(R.menu.menu)

        bottom_home.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_search -> Toast.makeText(this@HomeActivity,"Deseja pesquisar algo?", Toast.LENGTH_SHORT).show()
                R.id.action_config -> startActivity(Intent(this, SettingsActivity::class.java))
                R.id.action_refresh -> loading()
                R.id.action_logout -> startActivity(Intent(this, LoginActivity::class.java))
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
