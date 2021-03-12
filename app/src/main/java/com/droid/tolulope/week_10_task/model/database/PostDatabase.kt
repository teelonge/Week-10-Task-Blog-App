package com.droid.tolulope.week_10_task.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.droid.tolulope.week_10_task.model.data.Comment
import com.droid.tolulope.week_10_task.model.data.Post
import com.droid.tolulope.week_10_task.model.database.dao.PhotoDao
import com.droid.tolulope.week_10_task.model.database.dao.PostDao
import com.droid.tolulope.week_10_task.model.data.Photo
import com.droid.tolulope.week_10_task.model.database.dao.CommentDao

/**
 * Creates a database with a table of Post, Photo and Comment and holds a reference of each of the Dao's used to obtain data from
 * database
 */
@Database(entities = [Post::class,Photo::class,Comment::class], version = 1, exportSchema = false)
abstract class PostDatabase : RoomDatabase() {

    abstract fun postDao() : PostDao

    abstract fun photoDao() : PhotoDao

    abstract fun commentDao() : CommentDao

    companion object{
        // Writes to the field are made visible to other threads
        @Volatile
        private var INSTANCE: PostDatabase? = null

        private const val DATABASE_NAME = "posts"

        // Creates the post database when called and returns an instance of the postDatabase class
        fun createDatabase(context: Context) : PostDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        PostDatabase::class.java,
                        DATABASE_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}