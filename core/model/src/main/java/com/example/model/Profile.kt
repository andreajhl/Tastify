package com.example.model


data class Profile(
    val profileImageUri: String = "",
    val name: String = "",
    val lastName: String = "",
    val address: String = "",
    val streetNumber: String = "",
    val apartment: String = "",
    val floor: String = "",
    val phone: String = ""
)