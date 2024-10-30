package com.example.mytraver.data.local.datastore

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserSetting(
    val token: String,
    val onBoardingPassed: Boolean,
    val preferences: String,
    val username:String,
    val email:String,
    val phone:String,
    val avatar:String,
    val password:String,
) : Parcelable
