package com.example.ass7dialoglazycolumn

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Member(
    val name: String,
    val email: String,
    val salary: Int,
    val gender: String
) : Parcelable
