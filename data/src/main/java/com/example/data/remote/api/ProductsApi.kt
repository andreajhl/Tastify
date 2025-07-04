package com.example.data.remote.api

import com.example.data.remote.dtos.product.ProductDto
import com.example.data.remote.dtos.product.ProductUpdateDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductsApi {
    @GET("foods")
    suspend fun getProducts(): Response<List<ProductDto>>

    @PUT("foods")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body request: Response<ProductUpdateDto>
    ): List<ProductDto>
}