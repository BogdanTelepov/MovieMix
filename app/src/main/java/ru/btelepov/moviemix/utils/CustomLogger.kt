package ru.btelepov.moviemix.utils

import android.util.Log

interface CustomLogger {

    val tag: String

    fun showLog(message: String)

}