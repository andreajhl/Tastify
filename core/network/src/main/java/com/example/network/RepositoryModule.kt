package com.example.network

import com.example.remotes.repository.auth.AuthRepository
import com.example.remotes.repository.auth.AuthRepositoryImpl
import com.example.remotes.repository.cart.CartRepository
import com.example.remotes.repository.cart.CartRepositoryImpl
import com.example.remotes.repository.image.ImageRepository
import com.example.remotes.repository.image.ImageRepositoryImpl
import com.example.remotes.repository.order.OrderRepository
import com.example.remotes.repository.order.OrderRepositoryImpl
import com.example.remotes.repository.product.ProductRepository
import com.example.remotes.repository.product.ProductRepositoryImpl
import com.example.remotes.repository.user.UserRepository
import com.example.remotes.repository.user.UserRepositoryImpl
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