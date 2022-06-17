package ru.gb.veber.materialdesignapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PictureDTO(
    var date: String? = null,
    var explanation: String? = null,
    var hdurl: String? = null,
    @SerializedName("media_type")
    var mediaType: String? = null,
    @SerializedName("service_version")
    var serviceVersion: String? = null,
    var title: String? = null,
    var url: String? = null
) : Parcelable
