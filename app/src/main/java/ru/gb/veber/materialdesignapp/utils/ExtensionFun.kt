package ru.gb.veber.materialdesignapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


const val FORMAT_DATE = "yyyy-MM-dd"
fun Date.formatDate(): String = SimpleDateFormat(FORMAT_DATE, Locale.getDefault()).format(this)

@RequiresApi(Build.VERSION_CODES.O)
fun DateMinusDay(key: Long): String = LocalDate.now().minusDays(key).toString()

