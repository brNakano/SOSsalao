package com.example.sossalao

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.toolbar.*

class ProductDetailActivity : AppCompatActivity() {

    private val context: Context get() = this
    var product: Inventory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val product = intent.getSerializableExtra("inventory") as Inventory

        setSupportActionBar(toolbar)
        supportActionBar?.title = product?.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detail_product_name.text = product.name
        detail_product_make.text = product.make
        detail_product_description.text = product.description
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item?.itemId
        if  (id == R.id.action_delete) {

            AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage("Deseja excluir o produto")
                .setPositiveButton("Sim") {
                        dialog, which ->
                            taskDelete()
                }.setNegativeButton("Não") {
                        dialog, which -> dialog.dismiss()
                }.create().show()
        }
        else if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun taskDelete() {
        if (this.product != null && this.product is Inventory) {

            Thread {
                InventoryService.delete(this.product as Inventory)
                runOnUiThread {
                    finish()
                }
            }.start()
        }
    }
}