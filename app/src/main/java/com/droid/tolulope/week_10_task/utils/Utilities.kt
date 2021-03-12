package com.droid.tolulope.week_10_task.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.droid.tolulope.week_10_task.R
import com.droid.tolulope.week_10_task.adapter.HomepageAdapter
import com.droid.tolulope.week_10_task.model.data.PostAndPhoto
import com.droid.tolulope.week_10_task.model.data.PostDetail
import com.droid.tolulope.week_10_task.ui.HomepageFragmentDirections
import com.droid.tolulope.week_10_task.ui.SearchFragmentDirections

// Holds a list of local images used to represent a particular user
fun getImages(): MutableList<Int> {
    return mutableListOf(
        R.drawable.hermionedhface,
        R.drawable.danearys,
        R.drawable.oliver,
        R.drawable.carlsen_opt,
        R.drawable.naomi,
        R.drawable.jack,
        R.drawable.homeland,
        R.drawable.anthony,
        R.drawable.lebron,
        R.drawable.jack,
        R.drawable.serena
    )
}

/**
 * Loads an image into a given imageView, generates a url using GlideUrl from the given image url
 * and glide then loads the image into the view
 */
fun loadImage(context: Context, imgUrl: String, view: ImageView) {
    val url = GlideUrl(
        imgUrl, LazyHeaders.Builder()
            .addHeader("User-Agent", "your-user-agent")
            .build()
    )
    Glide.with(context)
        .load(url).apply {
            RequestOptions()
                .placeholder(R.drawable.loading_status_animation)
                .error(R.drawable.ic_error_image)
        }
        .into(view)
}

fun View.goto(action: NavDirections) {
    this.findNavController().navigate(action)
}

fun View.goto(id: Int) {
    this.findNavController().navigate(id)
}

// Searches the list in adapter for the query string
fun queryAllPosts(adapter: HomepageAdapter, searchView: SearchView) {
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            adapter.filter.filter(newText)
            return true
        }
    })
}

// Listener for the comment button home and search fragment
fun createListenerForComments(view: View, post: PostAndPhoto, fragId: Int) {
    val postDetail = PostDetail(post.post.title, post.post.body, post.post.id)
    when (fragId) {
        1 -> {
            val action = HomepageFragmentDirections.actionHomeFragmentToPostdetail(postDetail)
            view.goto(action)
        }
        2 -> {
            val action = SearchFragmentDirections.actionSearchFragmentToPostdetail(postDetail)
            view.goto(action)
        }
    }

}

// Displays a toast based on received string
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun validatePostInput(postTitle: String, postBody: String): Boolean {
    return when {
        postTitle.isNotEmpty() && postBody.isNotEmpty()->  true
        else -> false
    }
}