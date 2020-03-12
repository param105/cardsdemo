package com.example.retrodemo

import com.example.cardsdemo.UserDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url




interface ApiInterface {

    @GET
    fun getUsers(@Url url: String?): Call<UserDetails?>?
}