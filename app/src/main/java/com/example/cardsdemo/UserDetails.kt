package com.example.cardsdemo

data class UserDetails(
    val results: List<Result>
)

data class Result(
    val seed: String,
    val user: User,
    val version: String
)

data class User(
    val SSN: String,
    val cell: String,
    val email: String,
    val gender: String,
    val location: Location,
    val md5 : String,
    val name: Name,
    val password: String,
    val phone: String,
    val picture: String,
    val sha1 : String
)

data class Location(
    val city: String,
    val state: String,
    val street: String,
    val zip: String
)

data class Name(
    val first: String,
    val last: String,
    val title: String
)