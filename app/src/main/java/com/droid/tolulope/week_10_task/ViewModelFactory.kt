package com.droid.tolulope.week_10_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.droid.tolulope.week_10_task.model.repository.PostRepository


class ViewModelFactory(private val repository : PostRepository ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PostRepository::class.java)
            .newInstance(repository)
    }
}



