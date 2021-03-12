package com.droid.tolulope.week_10_task.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comment(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val body: String,
    val email: String,
    val name: String,
    val postId: Int
)