package ru.gb.veber.materialdesignapp.utils

import java.text.SimpleDateFormat
import java.util.*

const val NASA_BASE_URL = "https://api.nasa.gov/"

const val CHIP_TODAY = "CHIP_TODAY"
const val CHIP_YESTERDAY = "CHIP_YESTERDAY"
const val KEY_CHIP_YESTERDAY = 1L
const val CHIP_BEFORE_YD = "CHIP_BEFORE_YD"
const val KEY_CHIP_BEFORE_YD = 2L


const val FORMAT_DATE = "yyyy-MM-dd"
fun Date.formatDate(): String = SimpleDateFormat(FORMAT_DATE, Locale.getDefault()).format(this)
