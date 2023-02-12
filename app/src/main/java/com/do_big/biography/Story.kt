package com.do_big.biography

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    val uid: Int = 0,
    val type: Int = 0,
    val title: String? = null,
    val description: String? = null,
    val moral: String? = null,
    val imgUrl: String? = null,
    val rating: Int = 0,
    val tags: List<String>? = null
) : Parcelable

@Parcelize
data class Stories(
    val stories: List<Story>
) : Parcelable
