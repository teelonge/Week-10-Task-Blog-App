package com.droid.tolulope.week_10_task.ui

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.droid.tolulope.week_10_task.App
import com.droid.tolulope.week_10_task.R
import com.droid.tolulope.week_10_task.ViewModelFactory
import com.droid.tolulope.week_10_task.adapter.PostdetailAdapter
import com.droid.tolulope.week_10_task.databinding.FragmentPostdetailBinding
import com.droid.tolulope.week_10_task.model.data.Comment
import com.droid.tolulope.week_10_task.model.data.PostDetail
import com.droid.tolulope.week_10_task.utils.goto
import com.droid.tolulope.week_10_task.viewmodel.PostdetailViewModel

class PostdetailFragment : Fragment() {
    private var _binding: FragmentPostdetailBinding? = null
    private val args by navArgs<PostdetailFragmentArgs>()
    private lateinit var postdetail: PostDetail
    private lateinit var viewmodel: PostdetailViewModel
    private lateinit var postdetailAdapter: PostdetailAdapter
    private val binding
    get() = _binding!!
    private val repository by lazy { App.repository }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostdetailBinding.inflate(inflater, container, false)
        // Retrieves the post detail from the homeFragment
        postdetail = args.postdetail
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = ViewModelFactory(repository)
        viewmodel = ViewModelProvider(this, viewModelFactory)[PostdetailViewModel::class.java]

        // Initializes the adapter and sets it to the recycler view
        postdetailAdapter = PostdetailAdapter(requireContext())
        binding.postDetailRecycler.adapter = postdetailAdapter

        /**
         * Uses the postDetail to populate the respective views and sets an onclick listener to the
         * floating action button to navigate to another fragment to add a new comment to the post
         */
        binding.postTitle.text = postdetail.title
        binding.postBody.text = postdetail.body
        binding.fab.setOnClickListener {
            val action = PostdetailFragmentDirections.actionPostdetailToAddCommentFragment(postdetail)
            it.goto(action)
        }

        /**
         * Observes the commentList from the viewModel coming from the database and populates the
         * adapter accordingly
         */
        viewmodel.getCommentById(postdetail.postId).observe(viewLifecycleOwner, {
            postdetailAdapter.setUpComments(it as MutableList<Comment>)
        })


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}