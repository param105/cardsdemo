package com.example.roomdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cardsdemo.roomdatabase.FavUsers

@Database(entities = [FavUsers::class], version = 1, exportSchema = false)
abstract class UserDatabase:RoomDatabase() {
    abstract fun userDao():UserOperations

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            return (instance ?: synchronized(this) {
                    Room.databaseBuilder(context, UserDatabase::class.java, "myTinder").build()
            })
        }
    }
}