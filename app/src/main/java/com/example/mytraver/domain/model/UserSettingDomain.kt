package com.example.mytraver.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserSettingDomain(
    val token: String,
    val onBoardingPassed: Boolean,
    val preferences: String,
    val username:String,
    val email:String,
    val phone:String,
    val avatar:String,
    val password:String,
) : Parcelable
