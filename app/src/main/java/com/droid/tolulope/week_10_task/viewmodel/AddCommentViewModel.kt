package com.droid.tolulope.week_10_task.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droid.tolulope.week_10_task.model.repository.PostRepository
import com.droid.tolulope.week_10_task.model.data.Comment
import kotlinx.coroutines.launch

/**
 * AddCommentViewModel uses the repository to make calls to the network to create and add comments based
 * on a particular post id which gets added to the database
 */
class AddCommentViewModel(private val repository: PostRepository) : ViewModel() {
    fun addNewComment(postId : Int, comment: Comment){
        viewModelScope.launch {
            repository.addComment(postId,comment)
        }

    }
}