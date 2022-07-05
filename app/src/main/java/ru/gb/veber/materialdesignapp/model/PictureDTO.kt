package ru.gb.veber.materialdesignapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PictureDTO(
    var date: String,
    var explanation: String,
    var hdurl: String,
    @SerializedName("media_type")
    var mediaType: String,
    @SerializedName("service_version")
    var serviceVersion: String,
    var title: String,
    var url: String
) : Parcelable


data class EarthEpicServerResponseData(
    val identifier: String,
    val caption: String,
    val image: String,
    val version: String,
    val date: String,
)


data class MarsPhotosServerResponseData(
    @field:SerializedName("photos") val photos: ArrayList<MarsServerResponseData>,
)

data class MarsServerResponseData(
    @field:SerializedName("img_src") val imgSrc: String?,
    @field:SerializedName("earth_date") val earth_date: String?,
)
