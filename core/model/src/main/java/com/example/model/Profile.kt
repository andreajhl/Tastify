package com.example.model

data class Profile(
    val profileImageUri: String = "https://res.cloudinary.com/drioaxhhw/image/upload/v1753073062/avatarEmpty_qczkd2.webp",
    val name: String = "",
    val lastName: String = "",
    val address: String = "",
    val streetNumber: String = "",
    val apartment: String = "",
    val floor: String = "",
    val phone: String = ""
)