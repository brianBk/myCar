package com.ispace.mycar.repository

import androidx.lifecycle.LiveData
import com.ispace.mycar.data.CarDao
import com.ispace.mycar.data.FuelDao
import com.ispace.mycar.model.CarData
import com.ispace.mycar.model.FuelData

class MyCarRepository(private val carDao: CarDao, private val fuelDao: FuelDao) {

    val readAllCarData: LiveData<List<CarData>> = carDao.readAllCarData()
    val readAllFuelData: LiveData<List<FuelData>> = fuelDao.readAllFuelData()

    suspend fun addCar(some: CarData) {
        carDao.addCar(some)
    }
    suspend fun updateCar(some: CarData) {
        carDao.updateCar(some)
    }
    suspend fun deleteCar(trash: CarData) {
        carDao.deleteCar(trash)
    }
    suspend fun deleteAllCars() {
        carDao.deleteAllCarData()
    }
    suspend fun getModelWithPlate(newPlate: String): String {
        return carDao.getMakeWithPlate(newPlate)
    }

    //for the fuel data source
    suspend fun addFuel(some: FuelData) {
        fuelDao.addFuelData(some)
    }
    suspend fun updateFuel(some: FuelData) {
        fuelDao.updateFuelData(some)
    }
    suspend fun deleteFuel(trash: FuelData) {
        fuelDao.deleteFuelData(trash)
    }
    suspend fun deleteAllFuel() {
        fuelDao.deleteAllFuelData()
    }

}