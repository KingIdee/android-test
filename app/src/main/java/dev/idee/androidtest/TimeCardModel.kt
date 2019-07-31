package dev.idee.androidtest

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimeCardModel(
    val id: String? = "",
    val billRate : Double = 0.0,
    val projectName: String = "",
    val projectHours: Double = 0.0,
    val date: String = "",
    val startTime: String ="",
    val endTime: String) : Parcelable