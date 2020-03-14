package com.example.roomdemo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cardsdemo.roomdatabase.FavUsers

@Dao
interface UserOperations {

    @Query("SELECT * FROM fav_table")
    fun getAll(): List<FavUsers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: FavUsers)


}

