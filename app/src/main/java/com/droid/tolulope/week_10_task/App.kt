package com.droid.tolulope.week_10_task

import android.app.Application
import com.droid.tolulope.week_10_task.model.database.PostDatabase
import com.droid.tolulope.week_10_task.model.network.JsonPlaceholderClient
import com.droid.tolulope.week_10_task.model.repository.PostRepository
import com.droid.tolulope.week_10_task.model.repository.PostRepositoryImpl

// Provides a dependency to the app in a special way
class App : Application() {

    companion object{
        /**
         * This satisfies the dependencies of the repository and set instance and database private
         * since it's only the repository that is required outside the container
         */
        private lateinit var instance : App

        private val database : PostDatabase by lazy {
            PostDatabase.createDatabase(instance)
        }
        /**
         * Exposing the value as an interface, entire app uses the repository without creating it
         * manually, keeps the initialization in one place and usage in another
         */
        val repository : PostRepository by lazy {
            PostRepositoryImpl(
                database.postDao(),
                database.photoDao(),
                database.commentDao(),
                JsonPlaceholderClient.getPlaceholderEndPoint()
            )
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}