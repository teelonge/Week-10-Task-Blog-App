package com.droid.tolulope.week_10_task.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.droid.tolulope.week_10_task.model.data.Post
import com.droid.tolulope.week_10_task.model.data.PostAndPhoto

@Dao
interface PostDao {

    // Returns a list of post and photos
    @Query("SELECT * FROM Post ORDER BY id DESC")
    fun getPostAndPhoto() : LiveData<List<PostAndPhoto>>

    // Adds a new post gotten from the network to the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPost(post : Post)

    // Adds all the post from the initial network call
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllPost(post : List<Post>)

}