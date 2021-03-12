package com.droid.tolulope.week_10_task.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.droid.tolulope.week_10_task.model.repository.PostRepository
import com.droid.tolulope.week_10_task.model.data.Comment

/**
 * PostdetailViewModel uses the repository to make calls to the network to retrieve comments based
 * on a particular post id
 */
class PostdetailViewModel(private val repository: PostRepository) : ViewModel() {
    fun getCommentById(postId : Int?) : LiveData<List<Comment>>{
        return repository.getCommentById(postId)
    }
}