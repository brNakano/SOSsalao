package com.example.sossalao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_product_cadastro.*

class ProductCadastroActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_cadastro)
        setTitle("Adicionar Product")

        salveProduct.setOnClickListener {
            val product = Product()
            product.name = form_product_name.text.toString()
            product.make = form_product_make.text.toString()
            product.description = form_product_description.text.toString()

            /*taskAtualizar(product)*/
        }
    }

/*    private fun taskAtualizar(Product: Product) {
        // Thread para salvar a discilpina
        Thread {
            ProductService.save(Product)
            runOnUiThread {
                // após cadastrar, voltar para activity anterior
                finish()
            }
        }.start()
    }*/
}