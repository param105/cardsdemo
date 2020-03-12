package com.example.roomdemo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cardsdemo.roomdatabase.Users

@Dao
interface UserOperations {

    @Query("SELECT * FROM fav_table")
    fun getAll(): List<Users>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Array<out Any?>)


}

