package com.example.mytraver.data.remote

import com.example.mytraver.data.model.LoginInput
import com.example.mytraver.data.model.LoginResponseDto
import com.example.mytraver.data.remote.network.ApiService
import org.junit.Assert.*

import org.junit.Test
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RemoteSourceRepositoryImplTest {

    private lateinit var apiService: ApiService
    private lateinit var remoteSourceRepository: RemoteSourceRepositoryImpl

    @Before
    fun setUp() {
        // Mock ApiService
        apiService = mock(ApiService::class.java)

        // Inisialisasi RemoteSourceRepositoryImpl dengan mock ApiService
        remoteSourceRepository = RemoteSourceRepositoryImpl(apiService)
    }

    @Test
    fun `login should return expected LoginResponseDto`() = runTest {
        val email = "phincon@academy.com"
        val password = "password"
        val loginInput = LoginInput(email, password)
        val expectedResponse = LoginResponseDto(
            message = "Login success",
            data = LoginResponseDto.Data(
                firstName = "John",
                lastName = "Doe",
                email = "phincon@academy.com",
                phone = "08123456789",
                avatar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjr1sJ9GLmlT0dxCedk2yfiabM3s07EOfaxw&s",
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            ),
        )


        // Melakukan proses Stub pada apiService.login() untuk mengembalikan expectedResponse
        `when`(apiService.login(loginInput)).thenReturn(expectedResponse)

        // Act
        val result = remoteSourceRepository.login(email, password)

        // Assert
        assertEquals(expectedResponse, result)

        // Verify bahwa apiService.login() dipanggil dengan parameter yang benar
        verify(apiService).login(loginInput)
    }

    @Test
    fun `login should throw exception when api call fails`() = runTest {

        val email = "phincon@academy.id"
        val password = "password123"
        val loginInput = LoginInput(email, password)

        // Membuat apiService.login() melempar RuntimeException
        `when`(apiService.login(loginInput)).thenThrow(RuntimeException("Login failed"))

        // Act & Assert
        assertThrows(RuntimeException::class.java) {
            runBlocking {
                remoteSourceRepository.login(email, password)
            }
        }
    }
}
