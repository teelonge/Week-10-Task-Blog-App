package com.droid.tolulope.week_10_task.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.droid.tolulope.week_10_task.model.data.Comment
import com.droid.tolulope.week_10_task.model.data.Photo

@Dao
interface CommentDao {
    // Returns a list of comment based on the postId
    @Query("SELECT * FROM Comment WHERE postId = :postId")
    fun getCommentById(postId : Int?) : LiveData<List<Comment>>

    // Adds all the comment from the database into the list of comments
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllComment(comments : List<Comment>)

    // Adds a new comment to the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewComment(comment : Comment)

}