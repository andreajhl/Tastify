package com.example.remotes.api

import com.example.remotes.dtos.product.ProductDto
import com.example.remotes.dtos.product.ProductUpdateDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductsApi {
    @GET("foods")
    suspend fun getProducts(): Response<List<ProductDto>>

    @PUT("foods/{id}")
    suspend fun updateProduct(
        @Path("id") id: String,
        @Body request: ProductUpdateDto
    ): Response<ProductDto>
}