package com.example.sossalao

import com.google.gson.GsonBuilder
import java.io.Serializable

class Inventory : Serializable {

    var idProduct:Long = 1
    var name = ""
    var make = ""
    var description = ""

    override fun toString(): String {
        return "Produto(nome='$name')"
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }
}