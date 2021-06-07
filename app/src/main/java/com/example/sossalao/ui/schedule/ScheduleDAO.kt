package com.example.sossalao.ui.schedule

import androidx.room.*
import com.example.sossalao.ui.Inventory

@Dao
interface ScheduleDAO {
    @Query("SELECT * FROM schedule where idScheduling = :id")
    fun getById(id: Long) : Schedule?

    @Query("SELECT * FROM schedule")
    fun findAll(): List<Inventory>

    @Insert
    fun insert(schedule: Schedule)

    @Delete
    fun delete(schedule: Schedule)

    @Update
    fun update(schedule: Schedule)
}
