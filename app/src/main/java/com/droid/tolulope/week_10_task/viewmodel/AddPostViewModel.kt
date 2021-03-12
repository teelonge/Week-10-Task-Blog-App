package com.droid.tolulope.week_10_task.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droid.tolulope.week_10_task.model.repository.PostRepository
import com.droid.tolulope.week_10_task.model.data.Comment
import com.droid.tolulope.week_10_task.model.data.Post
import kotlinx.coroutines.launch

/**
 * AddPostViewModel uses the repository to make calls to the network to create and add post
 * which gets added to the database
 */
class AddPostViewModel(private val repository: PostRepository) : ViewModel() {

    fun addNewPost(post: Post) {
        viewModelScope.launch {
            repository.addPost(post)
        }

    }
}