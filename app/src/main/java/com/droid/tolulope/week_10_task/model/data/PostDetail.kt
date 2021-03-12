package com.droid.tolulope.week_10_task.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostDetail(
    val title : String,
    val body : String,
    val postId : Int? = null
) : Parcelable
