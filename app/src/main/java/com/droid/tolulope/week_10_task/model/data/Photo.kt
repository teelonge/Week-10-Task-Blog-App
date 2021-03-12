package com.droid.tolulope.week_10_task.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Photo(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "photosId")
    val id: Int,
    val albumId: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)