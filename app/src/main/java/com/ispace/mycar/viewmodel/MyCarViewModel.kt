package com.ispace.mycar.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ispace.mycar.data.MyCarDatabase
import com.ispace.mycar.model.CarData
import com.ispace.mycar.model.FuelData
import com.ispace.mycar.repository.MyCarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyCarViewModel(app: Application) : AndroidViewModel(app) {

    val readAllCarData: LiveData<List<CarData>>
    val readAllFuelData: LiveData<List<FuelData>>
    private val repository: MyCarRepository

    init {
        val carDao = MyCarDatabase.getDatabase(app).carDao()
        val fuelDao = MyCarDatabase.getDatabase(app).fuelDao()
        repository = MyCarRepository(carDao, fuelDao)
        readAllCarData = repository.readAllCarData
        readAllFuelData = repository.readAllFuelData
    }

    fun addCar(car: CarData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCar(car)
        }
    }
    fun updateCar(car: CarData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCar(car)
        }
    }
    fun deleteCar(trash: CarData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCar(trash)
        }
    }
    fun deleteAllCars() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCars()
        }
    }

    //fun getmodelWith

    fun getModelWithPlate(plates: String): String {
        var retModel = ""
        viewModelScope.launch(Dispatchers.IO) {
            retModel = repository.getModelWithPlate(plates)
        }
        return retModel
    }

    //For the fuel
    fun addFuel(some: FuelData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFuel(some)
        }
    }
    fun updateFuel(some: FuelData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFuel(some)
        }
    }
    fun deleteFuel(trash: FuelData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFuel(trash)
        }
    }
    fun deleteAllFuel() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFuel()
        }
    }
}