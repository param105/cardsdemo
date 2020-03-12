package com.example.cardsdemo.roomdatabase

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "fav_table")
data class Users (


    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "image") val imageUrl: String?):Parcelable


