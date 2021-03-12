package com.droid.tolulope.week_10_task.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droid.tolulope.week_10_task.model.repository.PostRepository
import kotlinx.coroutines.launch

/**
 * This viewModel serves as a bridge between the homepageView and the business logic which is the
 * model
 */

class HomepageViewModel(private val repository : PostRepository) : ViewModel() {

    // Upon initialization makes a call to the network to get all post, photo and comment api
    init {
        refreshPostFromRepository()
    }

    // LiveData that holds the value for all the post from the database
    val postAndPhotoList = repository.getPostAndPhoto()

    /**
     * Gets all post from the remote source using the repository and saves it in a mutableLiveData
     * in which a liveData saves it and its observed from the homepage for changes
     */
    private fun refreshPostFromRepository() {
        viewModelScope.launch {
            repository.addAllPost()
            repository.addAllPhoto()
            repository.addAllComment()
        }
    }



}