package com.example.cardsdemo

import com.example.retrodemo.ApiInterface

interface UserContract {

    interface  UserPresenter{

        fun getUserDetails(url: String, apiInterface: ApiInterface)
    }

    interface  UserViewLoader{

        fun setUserData(userDetails: UserDetails)
    }
}