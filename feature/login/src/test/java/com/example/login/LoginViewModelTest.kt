package com.example.login

import com.example.useCase.login.LoginUseCase
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val loginUseCase: LoginUseCase = mock()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        viewModel = LoginViewModel(loginUseCase)
    }

    @Test
    fun `executeLogin sets success state when use case returns success`() = runTest {
        whenever(loginUseCase("test@example.com", "Password1")).thenReturn(Result.success(Unit))

        viewModel.updateLoginField("email", "test@example.com")
        viewModel.updateLoginField("password", "Password1")

        viewModel.executeLogin()

        assertTrue(viewModel.loginState.value.isSuccess == true)
        assertFalse(viewModel.loginState.value.isLoading)
    }

    @Test
    fun `executeLogin sets error state when use case returns failure`() = runTest {
        whenever(
            loginUseCase(
                "test@example.com",
                "Password1"
            )
        ).thenReturn(Result.failure(Exception()))

        viewModel.updateLoginField("email", "test@example.com")
        viewModel.updateLoginField("password", "Password1")

        viewModel.executeLogin()

        assertTrue(viewModel.loginState.value.isError == true)
        assertFalse(viewModel.loginState.value.isLoading)
    }

    @Test
    fun `validateEmail returns false for invalid email`() {
        viewModel.updateLoginField("email", "invalid-email")
        val result = viewModel.validateEmail()
        assertFalse(result)
    }

    @Test
    fun `validatePassword returns false for short password`() {
        viewModel.updateLoginField("password", "123")
        val result = viewModel.validatePassword()
        assertFalse(result)
    }

}