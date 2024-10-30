package com.example.mytraver.data.model


import com.google.gson.annotations.SerializedName

class LoginResponseDto(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
) {
    class Data(
        @SerializedName("avatar")
        val avatar: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("firstName")
        val firstName: String,
        @SerializedName("lastName")
        val lastName: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("token")
        val token: String
    )
}