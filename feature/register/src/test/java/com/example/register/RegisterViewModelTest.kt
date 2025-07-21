package com.example.register

import com.example.useCase.register.RegisterUseCase
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class RegisterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val registerUseCase: RegisterUseCase = mock()
    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setup() {
        viewModel = RegisterViewModel(registerUseCase)
    }

    @Test
    fun `updateRegisterField updates the correct field`() {
        viewModel.updateRegisterField("name", "Andrea")
        assertEquals("Andrea", viewModel.registerData.value.name)

        viewModel.updateRegisterField("email", "andrea@example.com")
        assertEquals("andrea@example.com", viewModel.registerData.value.email)
    }

    @Test
    fun `validateEmail should set error when email is invalid`() {
        viewModel.updateRegisterField("email", "invalid")
        val isValid = viewModel.validateEmail()
        assertFalse(isValid)
        assertTrue(viewModel.errorMsg.value.email!!)
    }

    @Test
    fun `isValidateData should return false for invalid data`() {
        val result = viewModel.isValidateData()
        assertFalse(result)
    }

    @Test
    fun `executeRegister should not call use case if data is invalid`() = runTest {
        viewModel.updateRegisterField("email", "invalid")
        viewModel.executeRegister()

        verify(registerUseCase, never()).invoke(any())
    }

    @Test
    fun `executeRegister should set isSuccess true on success`() = runTest {
        viewModel.updateRegisterField("name", "Andrea")
        viewModel.updateRegisterField("lastName", "Hernandez")
        viewModel.updateRegisterField("email", "andrea@example.com")
        viewModel.updateRegisterField("password", "123456")
        viewModel.updateRegisterField("repeatPassword", "123456")

        whenever(registerUseCase(any())).thenReturn(true)

        viewModel.executeRegister()

        advanceUntilIdle()

        assertTrue(viewModel.registerState.value.isSuccess == true)
    }

    @Test
    fun `executeRegister should set isError true on failure`() = runTest {
        viewModel.updateRegisterField("name", "Andrea")
        viewModel.updateRegisterField("lastName", "Hernandez")
        viewModel.updateRegisterField("email", "andrea@example.com")
        viewModel.updateRegisterField("password", "123456")
        viewModel.updateRegisterField("repeatPassword", "123456")

        whenever(registerUseCase(any())).thenReturn(false)

        viewModel.executeRegister()

        advanceUntilIdle()

        assertTrue(viewModel.registerState.value.isError == true)
    }
}