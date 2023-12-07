package com.ispace.mycar.data

import android.os.FileObserver.DELETE
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ispace.mycar.model.CarData

@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCar(car: CarData)

    @Update
    suspend fun updateCar(car: CarData)

    @Delete
    suspend fun deleteCar(car: CarData)

    @Query("DELETE FROM car_table")
    suspend fun deleteAllCarData()

    @Query("SELECT * FROM car_table")
    fun readAllCarData(): LiveData<List<CarData>>

    @Transaction
    @Query("SELECT car_make FROM car_table WHERE number_plate = :numberPlate")
    suspend fun getMakeWithPlate(numberPlate: String): String
}