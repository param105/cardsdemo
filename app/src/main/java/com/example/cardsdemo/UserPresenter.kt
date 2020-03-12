package com.example.cardsdemo

import android.util.Log
import com.example.retrodemo.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserPresenter(val viewLoader: UserContract.UserViewLoader) :UserContract.UserPresenter{


    override fun getUserDetails(
        url: String,
        apiInterface: ApiInterface) {


        val call: Call<UserDetails?>? = apiInterface.getUsers(url)

        call!!.enqueue(object :Callback<UserDetails?>{


            override fun onFailure(call: Call<UserDetails?>, t: Throwable) {


            }

            override fun onResponse(call: Call<UserDetails?>, response: Response<UserDetails?>) {

                val userDetails=response.body()

                if (userDetails != null) {
                    viewLoader.setUserData(userDetails)
                }
            }


        })



    }


}