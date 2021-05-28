package com.example.sossalao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDAO {
    @Query("SELECT * FROM inventory where idProduct = :id")
    fun getById(id: Long) : Inventory?

    @Query("SELECT * FROM inventory")
    fun findAll(): List<Inventory>

    @Insert
    fun insert(product: Inventory)

    @Delete
    fun delete(product: Inventory)
}