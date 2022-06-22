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


data class PODServerResponseData(
    @field:SerializedName("copyright") val copyright: String?,
    @field:SerializedName("date") val date: String?,
    @field:SerializedName("explanation") val explanation: String?,
    @field:SerializedName("media_type") val mediaType: String?,
    @field:SerializedName("title") val title: String?,
    @field:SerializedName("url") val url: String?,
    @field:SerializedName("hdurl") val hdurl: String?
)

data class SolarFlareResponseData(
    val flrID: String,
    val beginTime: String,
    val peakTime: String,
    val endTime: Any? = null,
    val classType: String,
    val sourceLocation: String,
    val activeRegionNum: Long,
    val link: String
)
