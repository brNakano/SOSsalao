package com.example.sossalao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.GsonBuilder
import java.io.Serializable

@Entity(tableName = "inventory")
class Inventory : Serializable {

    @PrimaryKey
    var idProduct:Long = 1
    var name = ""
    var make = ""
    var description = ""

    override fun toString(): String {
        return "{'name': ${this.name.toString()}, 'make': ${this.make.toString()}, 'description': ${this.description.toString()}}"
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }
}