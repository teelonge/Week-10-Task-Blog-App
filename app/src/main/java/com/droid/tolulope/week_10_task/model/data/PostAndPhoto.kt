package com.droid.tolulope.week_10_task.model.data

import androidx.room.Embedded
import androidx.room.Relation


data class PostAndPhoto(
    @Embedded
    val post: Post,
    @Relation(parentColumn = "id", entityColumn = "photosId")
    val photo : Photo?
)