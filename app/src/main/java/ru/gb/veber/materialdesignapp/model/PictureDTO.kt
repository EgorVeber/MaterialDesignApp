package ru.gb.veber.materialdesignapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PictureDTO(
    var date: String? = null,
    var explanation: String? = null,
    var hdurl: String? = null,
    var media_type: String? = null,
    var service_version: String? = null,
    var title: String? = null,
    var url: String? = null
):Parcelable