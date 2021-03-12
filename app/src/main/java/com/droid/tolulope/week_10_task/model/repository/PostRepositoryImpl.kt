package com.droid.tolulope.week_10_task.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.droid.tolulope.week_10_task.model.data.*
import com.droid.tolulope.week_10_task.model.database.dao.CommentDao
import com.droid.tolulope.week_10_task.model.database.dao.PhotoDao
import com.droid.tolulope.week_10_task.model.database.dao.PostDao
import com.droid.tolulope.week_10_task.model.network.JsonPlaceholderEndpoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

/**
 * This repository class abstracts access to the API endpoint, provides a clean API for
 * data access to the rest of the application, manages queries and communicates with the
 * remote data source according to request from the viewModel populates the database and retrieves
 * data from the database
 */
class PostRepositoryImpl(
    private val postDao: PostDao,
    private val photoDao: PhotoDao,
    private val commentDao: CommentDao,
    private val jsonPlaceholderEndpoint: JsonPlaceholderEndpoint
) : PostRepository {

    // Retrieves post from the network and adds to the database
    override suspend fun addAllPost() {
        withContext(IO) {
            try {
                val posts = jsonPlaceholderEndpoint.getAllPosts()
                postDao.addAllPost(posts)
            } catch (e: Exception) {
                Log.d("Network Error", "${e.message}")
            }
        }
    }

    // Retrieves photos from the network and adds to the database
    override suspend fun addAllPhoto() {
        withContext(IO) {
            try {
                val photos = jsonPlaceholderEndpoint.getAllPhotos()
                photoDao.addAllPhoto(photos)
            } catch (e: Exception) {
                Log.d("Network Error", "${e.message}")
            }

        }
    }

    // Retrieves comments from the network and adds to the database
    override suspend fun addAllComment() {
        withContext(IO) {
            try {
                val comments = jsonPlaceholderEndpoint.getAllComments()
                commentDao.addAllComment(comments)
            } catch (e: Exception) {
                Log.d("Network Error", "${e.message}")
            }

        }
    }

    // Creates and retrieves new post from the network and adds to the database
    override suspend fun addPost(post: Post) {
        withContext(IO) {
            try {
                val newPost = jsonPlaceholderEndpoint.createNewPost(post)
                newPost.id = null
                postDao.addPost(newPost)
            } catch (e: Exception) {
                Log.d("Network Error", "${e.message}")
            }

        }
    }

    // Creates and retrieves new comment from the network and adds to the database
    override suspend fun addComment(id: Int, comment: Comment) {
        withContext(IO) {
            try {
                val newComment = jsonPlaceholderEndpoint.addNewComment(id, comment)
                newComment.id = null
                commentDao.addNewComment(newComment)
            } catch (e: Exception) {
                Log.d("Network Error", "${e.message}")
            }

        }
    }

    // Retrieves a combination of the post and photos from the database
    override fun getPostAndPhoto(): LiveData<List<PostAndPhoto>> {
        return postDao.getPostAndPhoto()
    }

    // Retrieves comment from the database based on the post Id
    override fun getCommentById(postId: Int?): LiveData<List<Comment>> {
        return commentDao.getCommentById(postId)
    }

}