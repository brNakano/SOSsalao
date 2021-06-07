package com.example.sossalao.ui

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.GsonBuilder
import java.io.FileDescriptor
import java.io.Serializable

@Entity(tableName = "inventory")
class Inventory : Serializable {

    @PrimaryKey
    var idProduct:Long = 1
    var name = ""
    var make = ""
    var description = ""

    override fun toString(): String {
        return "{'idProduct': ${this.idProduct},'name': '${this.name.toString()}', 'make': '${this.make.toString()}', 'description': '${this.description.toString()}'}"
    }

    fun toJson(): String {
        val invetory = PostInventory(
            name = this.name,
            make = this.make,
            description = this.description
        )
        return GsonBuilder().create().toJson(invetory)
    }
}

data class PostInventory(val name: String, val make: String, val description: String)