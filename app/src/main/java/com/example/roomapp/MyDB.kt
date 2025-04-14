package com.example.roomapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1)
abstract class MyDB : RoomDatabase(){

    // Getting DAO instance
    abstract fun itemDAO(): ItemDao

    // Accessing the methods to create or get the Database
    companion object{
        // Singleton pattern
        private var Instance: MyDB? = null

        // if the Instance is not null, return it
        // otherwise create a new database instance

        fun getDatabase(context: Context): MyDB{
            // synchronized block ensures thread safety by
            // allowing only one instance
            return Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    MyDB::class.java,
                    "item_database"
                ).build()
                    .also{
                        Instance = it
                    }
            }
        }

    }
}