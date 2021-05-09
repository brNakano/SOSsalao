package com.example.sossalao

import android.content.Context

object ProductService {

    fun getProducts (context: Context): List<Product> {
        val products = mutableListOf<Product>()

        // criar 10 Products
        for (i in 1..10) {
            val d = Product()
            d.name = "$i"
            d.make = "Marca: $i"
            d.description = "Descrição $i"
            products.add(d)
        }

        return products
    }


}