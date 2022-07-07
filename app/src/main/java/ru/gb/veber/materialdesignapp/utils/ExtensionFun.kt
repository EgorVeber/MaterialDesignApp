package ru.gb.veber.materialdesignapp.utils

import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


const val FORMAT_DATE = "yyyy-MM-dd"
fun Date.formatDate(): String = SimpleDateFormat(FORMAT_DATE, Locale.getDefault()).format(this)

fun dataFromString(dateString: String) =
    SimpleDateFormat(FORMAT_DATE, Locale.getDefault()).parse(dateString)

@RequiresApi(Build.VERSION_CODES.O)
fun DateMinusDay(key: Long): String = LocalDate.now().minusDays(key).toString()

fun takeDate(count: Int): String {
    val currentDate = Calendar.getInstance()
    currentDate.add(Calendar.DAY_OF_MONTH, count)
    val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    format1.timeZone = TimeZone.getTimeZone("EST")
    return format1.format(currentDate.time)
}

fun initDatePicker(date: Date, datePicker: DatePicker) {
    Calendar.getInstance().also {
        it.time = date
        datePicker.init(it[Calendar.YEAR], it[Calendar.MONTH], it[Calendar.DAY_OF_MONTH], null)
    }
}

fun getDateFromDatePicker(datePicker: DatePicker): Date {
    return Calendar.getInstance().apply {
        this[Calendar.YEAR] = datePicker.year
        this[Calendar.MONTH] = datePicker.month
        this[Calendar.DAY_OF_MONTH] = datePicker.dayOfMonth
    }.time
}
fun findVideoId(url: String): String {
    return url.substringAfterLast('/').substringBefore('?')
}