package com.example.remotes.auth

import com.example.remotes.api.AuthApi
import com.example.remotes.dtos.auth.AuthDto
import com.example.remotes.dtos.auth.RegisterDto
import com.example.remotes.dtos.user.UserLoginDto
import com.example.remotes.repository.auth.AuthRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class AuthRepositoryImplTest {

    private val authApi: AuthApi = mock()
    private lateinit var repository: AuthRepositoryImpl

    @Before
    fun setup() {
        repository = AuthRepositoryImpl(authApi)
    }

    @Test
    fun `login should delegate to AuthApi and return response`() = runTest {
        val request = AuthDto("jose.perez@example.com", "hashedPassword")
        val expectedResponse: Response<UserLoginDto> =
            Response.success(UserLoginDto("1", "jose.perez@example.com", "Jose", "Perez"))

        whenever(authApi.login(request)).thenReturn(expectedResponse)

        val result = repository.login(request)

        verify(authApi).login(request)
        assertEquals(expectedResponse, result)
    }

    @Test
    fun `register should delegate to AuthApi and return response`() = runTest {
        val request = RegisterDto("John", "Doe", "john@example.com", "hashedPassword")
        val expectedResponse: Response<UserLoginDto> =
            Response.success(UserLoginDto("1", "john.doe@example.com", "John", "Doe"))

        whenever(authApi.register(request)).thenReturn(expectedResponse)

        val result = repository.register(request)

        verify(authApi).register(request)
        assertEquals(expectedResponse, result)
    }
}