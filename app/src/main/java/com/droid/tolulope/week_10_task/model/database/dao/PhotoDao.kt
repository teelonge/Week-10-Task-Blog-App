package com.droid.tolulope.week_10_task.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.droid.tolulope.week_10_task.model.data.Photo

@Dao
interface PhotoDao {
    // Adds all the photos to the databases
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllPhoto(photos : List<Photo>)

    // Retrieves all the photos from the database
    @Query("SELECT * FROM Photo")
    fun getAllPhoto(): LiveData<List<Photo>>
}