package io.github.peerpongsam.example.recyclerviewselection

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    val url: String,
    var isSelected: Boolean = false
) : Parcelable
