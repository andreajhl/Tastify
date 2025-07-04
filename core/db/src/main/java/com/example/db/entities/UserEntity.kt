package com.example.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "last_name")
    val surname: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "nationality")
    val nationality: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null
)