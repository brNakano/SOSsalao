package com.example.sossalao.ui.people

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.GsonBuilder
import java.io.Serializable

@Entity(tableName = "people")
class People : Serializable {

    @PrimaryKey
    var idPeople:Long = 1
    var name = ""
    var phoneNumber = ""
    var email = ""
    var typePeople = 0


    fun toJsonPost(): String {
        val people = PostPeople(
            name = this.name,
            phoneNumber = this.phoneNumber,
            email = this.email,
            typePeople = this.typePeople

        )
        return GsonBuilder().create().toJson(people)
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }

}

data class PostPeople(
    val name: String,
    val phoneNumber: String,
    val email: String,
    val typePeople: Int
)