package com.example.mytraver.domain.model

data class LoginResponse(
    val username: String,
    val email: String,
    val phone: String,
    val avatar: String,
    val token: String
)
