package com.ispace.mycar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ispace.mycar.model.CarData
import com.ispace.mycar.model.FuelData

@Database(entities = [CarData::class, FuelData::class], version = 1, exportSchema = false)
abstract class MyCarDatabase : RoomDatabase() {

    abstract fun carDao(): CarDao
    abstract fun fuelDao(): FuelDao

    companion object {

        @Volatile
        private var INSTANCE: MyCarDatabase? = null

        fun getDatabase(context: Context): MyCarDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyCarDatabase::class.java,
                    "mycar_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}