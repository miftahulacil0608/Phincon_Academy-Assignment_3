package com.example.mytraver.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TypeReferences(
    val type:String,
    val image:Int
):Parcelable
