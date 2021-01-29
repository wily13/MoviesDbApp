package com.wily.moviesdbapp.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenresItem(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String

): Parcelable