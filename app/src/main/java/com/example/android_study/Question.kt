package com.example.android_study

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean, var right : Boolean = false)