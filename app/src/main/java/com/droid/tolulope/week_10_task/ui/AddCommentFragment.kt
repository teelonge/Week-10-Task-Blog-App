package com.droid.tolulope.week_10_task.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.droid.tolulope.week_10_task.App
import com.droid.tolulope.week_10_task.ViewModelFactory
import com.droid.tolulope.week_10_task.databinding.FragmentAddcommentBinding
import com.droid.tolulope.week_10_task.model.data.Comment
import com.droid.tolulope.week_10_task.model.data.PostDetail
import com.droid.tolulope.week_10_task.utils.goto
import com.droid.tolulope.week_10_task.viewmodel.AddCommentViewModel

/**
 * This fragment creates a new comment and sends it to the viewmodel to be posted to the network and
 * added to the database
 */
class AddCommentFragment : Fragment() {
    private lateinit var viewModel : AddCommentViewModel
    private lateinit var postDetail : PostDetail
    private val args by navArgs<AddCommentFragmentArgs>()
    private val repository by lazy { App.repository }
    private var _binding : FragmentAddcommentBinding? = null
    private val binding
    get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddcommentBinding.inflate(inflater,container,false)

        // Gets the postdetail from the postDetail to get access to the commentId
        postDetail = args.postDetail
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory)[AddCommentViewModel::class.java]

        /**
         * On click of the button new comment is created and user is navigated to the postDetails
         * where the created comment is viewed
         */
        binding.btnCreateComment.setOnClickListener {
            viewModel.addNewComment(postDetail.postId!!,createComment())
            Handler(Looper.getMainLooper()).postDelayed({
                val action = AddCommentFragmentDirections.actionAddCommentFragmentToPostdetail(postDetail)
                it.goto(action)
            }, 1000)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createComment() : Comment{
        val commentBody = binding.edtCommentBody.text.toString()
        val commentName = binding.txtCommentName.text.toString()
        val commentEmail = binding.txtCommentEmail.text.toString()
        return Comment(body = commentBody,postId = postDetail.postId!!,email = commentEmail,name = commentName)
    }


}