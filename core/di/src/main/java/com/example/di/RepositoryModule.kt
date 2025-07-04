package com.example.di

import com.example.data.remote.repository.auth.AuthRepository
import com.example.data.remote.repository.auth.AuthRepositoryImpl
import com.example.data.remote.repository.cart.CartRepository
import com.example.data.remote.repository.cart.CartRepositoryImpl
import com.example.data.remote.repository.image.ImageRepository
import com.example.data.remote.repository.image.ImageRepositoryImpl
import com.example.data.remote.repository.order.OrderRepository
import com.example.data.remote.repository.order.OrderRepositoryImpl
import com.example.data.remote.repository.product.ProductRepository
import com.example.data.remote.repository.product.ProductRepositoryImpl
import com.example.data.remote.repository.user.UserRepository
import com.example.data.remote.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(impl: CartRepositoryImpl): CartRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(impl: OrderRepositoryImpl): OrderRepository

    @Binds
    abstract fun binImageRepository(impl: ImageRepositoryImpl): ImageRepository
}