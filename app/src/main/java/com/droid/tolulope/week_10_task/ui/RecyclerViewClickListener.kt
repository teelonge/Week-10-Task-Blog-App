package com.droid.tolulope.week_10_task.ui

import android.view.View
import com.droid.tolulope.week_10_task.model.data.PostAndPhoto

// Interface for handling click events of recyclerView contents
interface RecyclerViewClickListener {
    fun onRecyclerViewItemClicked(view : View, post : PostAndPhoto)
}