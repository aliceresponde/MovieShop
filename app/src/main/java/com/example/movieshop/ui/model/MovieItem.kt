package com.example.movieshop.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class MovieItem(
    val id: Int,
    val name: String,
    val date: String,
    val overview: String,
    val imageUrl: String,
    var quantity: Int
) : Parcelable

