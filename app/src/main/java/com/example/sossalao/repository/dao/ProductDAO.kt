package com.example.sossalao.repository.dao

import androidx.room.*
import com.example.sossalao.ui.Inventory

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