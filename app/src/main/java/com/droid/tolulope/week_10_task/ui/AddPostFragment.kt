package com.droid.tolulope.week_10_task.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.droid.tolulope.week_10_task.App
import com.droid.tolulope.week_10_task.R
import com.droid.tolulope.week_10_task.ViewModelFactory
import com.droid.tolulope.week_10_task.databinding.FragmentAddpostBinding
import com.droid.tolulope.week_10_task.model.data.Post
import com.droid.tolulope.week_10_task.utils.goto
import com.droid.tolulope.week_10_task.utils.showToast
import com.droid.tolulope.week_10_task.utils.validatePostInput
import com.droid.tolulope.week_10_task.viewmodel.AddPostViewModel

/**
 * This fragment creates a new post and sends it to the viewModel to be posted to the network and
 * added to the database
 */
class AddPostFragment : Fragment() {

    private lateinit var viewModel: AddPostViewModel
    private val repository by lazy { App.repository }
    private var _binding: FragmentAddpostBinding? = null
    private val binding
    get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddpostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddPostViewModel::class.java]

        /**
         * On click of the button new comment is created and user is navigated to the postDetails
         * where the created comment is viewed
         */
        binding.btnCreatePost.setOnClickListener {
            val post = createPost()
            if (post != null){
                viewModel.addNewPost(post)
                Handler(Looper.getMainLooper()).postDelayed({
                    val action = AddPostFragmentDirections.actionAddPostToHomeFragment()
                    it.goto(action)
                }, 1000)
            }else{
                showToast(requireContext(),"Enter correct post details")
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createPost(): Post? {
        val postTitle = binding.edtNewPostTitle.text.toString()
        val postBody = binding.edtNewPostBody.text.toString()
        val postUserId = 11
       return when {
           validatePostInput(postTitle,postBody) -> {
               Post(title = postTitle, userId = postUserId, body = postBody)
           }
           else -> null
       }

    }


}