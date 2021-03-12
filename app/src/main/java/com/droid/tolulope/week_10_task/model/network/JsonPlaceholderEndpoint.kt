package com.droid.tolulope.week_10_task.model.network


import com.droid.tolulope.week_10_task.model.data.Comment
import com.droid.tolulope.week_10_task.model.data.Photo
import com.droid.tolulope.week_10_task.model.data.Post
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface JsonPlaceholderEndpoint {

    // Calls the api to retrieve all the posts
    @GET("posts")
    suspend fun getAllPosts(): List<Post>

    // Retrieves all the photos from the api
    @GET("photos")
    suspend fun getAllPhotos(): List<Photo>

    // gets the list of comment based on the user id
    @GET("comments")
    suspend fun getAllComments(): List<Comment>

    // Uploads newly created post to the api
    @POST("posts")
    suspend fun createNewPost(@Body post: Post): Post

    // Uploads newly added comment to the api
    @POST("posts/{id}/comments")
    suspend fun addNewComment(@Path("id") id: Int, @Body comment: Comment): Comment
}