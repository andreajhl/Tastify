package com.example.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.db.daos.CartDao
import com.example.db.daos.OrderDao
import com.example.db.daos.ProductDao
import com.example.db.daos.UserDao
import com.example.db.entities.CartItemEntity
import com.example.db.entities.OrderEntity
import com.example.db.entities.OrderItemEntity
import com.example.db.entities.ProductEntity
import com.example.db.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        ProductEntity::class,
        CartItemEntity::class,
        OrderEntity::class,
        OrderItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
}