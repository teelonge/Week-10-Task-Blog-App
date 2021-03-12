package com.droid.tolulope.week_10_task.adapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.droid.tolulope.week_10_task.R
import com.droid.tolulope.week_10_task.ui.RecyclerViewClickListener
import com.droid.tolulope.week_10_task.model.data.PostAndPhoto
import com.droid.tolulope.week_10_task.utils.getImages
import com.droid.tolulope.week_10_task.utils.loadImage
import com.droid.tolulope.week_10_task.utils.showToast
import java.util.*


/**
 * An adapter that binds post received from the network call into a recycler view for efficient display of the
 * items in a view. It creates and binds the view for the userId, id ,postTitle, postBody and a postImage
 */
class HomepageAdapter(
    private val context: Context, private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<HomepageAdapter.HomepageViewHolder>(),
    Filterable {

    // This will hold the list of posts and photos from the database
    private var postAndPhoto = mutableListOf<PostAndPhoto>()

    // Interim hold to save the original list of posts before search operation is performed
    private var ogPosts = mutableListOf<PostAndPhoto>()

    // Local images for identifying specific userId
    private var postImages = getImages()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomepageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return HomepageViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomepageViewHolder, position: Int) {
        holder.bind(postAndPhoto[position])
    }

    override fun getItemCount(): Int = postAndPhoto.size

    /**
     * This method constrains data based on a filtering pattern which is the constraint, its operation
     * is performed asynchronously, as the search input comes from the homepage, performs the filter
     * and data that matches the search pattern is added to a new list which is displayed on the UI
     */
    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchString = constraint.toString()
                if (searchString.isEmpty()) {
                    postAndPhoto = emptyArray<PostAndPhoto>().toMutableList()
                } else {
                    postAndPhoto = ogPosts
                    val tempSearchedPosts = mutableListOf<PostAndPhoto>()
                    for (post in postAndPhoto) {
                        if (post.post.title.toLowerCase(Locale.ROOT).trim()
                                .contains(searchString)
                        ) {
                            tempSearchedPosts.add(post)
                        }
                    }
                    postAndPhoto = tempSearchedPosts
                }

                val filterPosts = FilterResults()
                filterPosts.values = postAndPhoto
                return filterPosts
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                postAndPhoto = results?.values as MutableList<PostAndPhoto>
                notifyDataSetChanged()
            }

        }
    }

    // ViewHolder class for the recyclerView
    inner class HomepageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtPostTitle = itemView.findViewById<TextView>(R.id.txtPostTitle)
        private val txtPostBody = itemView.findViewById<TextView>(R.id.txtPostBody)
        private val imgPostSmall = itemView.findViewById<ImageView>(R.id.imgPostSmallImage)
        private val imgPostImage = itemView.findViewById<ImageView>(R.id.imgPostImage)
        private val txtUserId = itemView.findViewById<TextView>(R.id.txtUserId)
        private val imgBookmark = itemView.findViewById<ImageView>(R.id.btnBookmark)
        private val btnComment = itemView.findViewById<ImageView>(R.id.btnComment)
        private val btnLike = itemView.findViewById<ImageView>(R.id.btnLike)
        private var collapseIntro = false

        // Bug moved like and bookmarked to data class
        fun bind(post: PostAndPhoto) {
            btnLike.setOnClickListener {
                when (post.post.liked) {
                    false -> {
                        btnLike.setImageResource(R.drawable.ic_red_heart)
                        showToast(context, "You liked this post")
                        post.post.liked = true
                    }
                    true -> {
                        btnLike.setImageResource(R.drawable.heart_svgrepo_com)

                        post.post.liked = false
                    }
                }
            }
            // Sets the image for each user
            imgPostSmall.setImageResource(postImages[post.post.userId - 1])

            // Sets the post title
            txtPostTitle.text = post.post.title

            // Sets the post body
            txtPostBody.text = HtmlCompat.fromHtml(
                context.getString(
                    R.string.placeholder_title_body, post.post.title, post.post.body
                ), HtmlCompat.FROM_HTML_MODE_LEGACY
            )

            // Sets the userId and Id value
            txtUserId.text =
                context.getString(R.string.placeholder_user_id, post.post.userId, post.post.id)

            // Loads the image for each post from the network call using its url and employing glide
            post.photo?.let { loadImage(context, it.url, imgPostImage) }

            // Sets a clickListener for the cardView which launches the postDetails activity
            btnComment.setOnClickListener {
                listener.onRecyclerViewItemClicked(it, post)
            }

            // Sets a clickListener on the post body to expand or collapse it
            txtPostBody.setOnClickListener {
                if (collapseIntro) {
                    txtPostBody.maxLines = 3
                    collapseIntro = false
                } else {
                    txtPostBody.maxLines = Int.MAX_VALUE
                    collapseIntro = true
                }
            }

            imgBookmark.setOnClickListener {
                when (post.post.bookmarked) {
                    false -> {
                        imgBookmark.setImageResource(R.drawable.bookmark_svgrepo_com)
                        showToast(context, "${post.post.id} added to bookmarks")
                        post.post.bookmarked = true

                    }
                    true -> {
                        imgBookmark.setImageResource(R.drawable.bookmark_svgrepo_com_2)
                        post.post.bookmarked = false
                    }
                }
            }
        }
    }

    /*
    Receives the posts retrieved from the network call and sets it
    up here in the adapter
     */
    fun setupPosts(posts: MutableList<PostAndPhoto>) {
        postAndPhoto.clear()
        postAndPhoto.addAll(posts)
        this.ogPosts = posts
        notifyDataSetChanged()
    }


}