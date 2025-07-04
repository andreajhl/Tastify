package com.example.db

import android.content.Context
import androidx.room.Room
import com.example.db.daos.CartDao
import com.example.db.daos.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext, AppDatabase::class.java, "peya-database"
        ).fallbackToDestructiveMigration(true).build()

    }

    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun provideCartItemDao(database: AppDatabase): CartDao {
        return database.cartDao()
    }
}