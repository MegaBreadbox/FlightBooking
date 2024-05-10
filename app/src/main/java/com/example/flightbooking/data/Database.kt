package com.example.flightbooking.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntry:: class], [FlightEntry:: class], version = 1, exportSchema = false)
abstract class FlightDatabase: RoomDatabase() {
    abstract fun flightEntryDao(): FlightEntryDao

    abstract fun favoriteEntryDao(): FavoriteEntryDao

    companion object{
        @Volatile
        private var instance: FlightDatabase? = null

        fun getDatabase(context: Context): FlightDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, FlightDatabase:: class.java, "flight_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }



    }


}