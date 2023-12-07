package com.ispace.mycar.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ispace.mycar.model.FuelData

@Dao
interface FuelDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFuelData(fuel: FuelData)

    @Update
    suspend fun updateFuelData(fuel: FuelData)

    @Delete
    suspend fun deleteFuelData(fuel: FuelData)

    @Query("DELETE FROM fuel_table")
    suspend fun deleteAllFuelData()

    @Query("SELECT * FROM fuel_table ORDER BY id ASC")
    fun readAllFuelData(): LiveData<List<FuelData>>
}