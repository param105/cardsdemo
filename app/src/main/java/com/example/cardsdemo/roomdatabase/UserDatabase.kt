package com.example.roomdemo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cardsdemo.roomdatabase.Users

@Database(entities = [Users::class], version = 1)
abstract class UserDatabase:RoomDatabase() {

    abstract fun userDao():UserOperations
}