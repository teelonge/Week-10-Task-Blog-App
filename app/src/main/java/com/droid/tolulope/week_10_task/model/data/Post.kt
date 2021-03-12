package com.droid.tolulope.week_10_task.model.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val userId: Int,
    val body: String,
    val title: String,
    var liked : Boolean = false,
    var bookmarked : Boolean = false,

)