package com.droid.tolulope.week_10_task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.droid.tolulope.week_10_task.App
import com.droid.tolulope.week_10_task.ViewModelFactory
import com.droid.tolulope.week_10_task.adapter.HomepageAdapter
import com.droid.tolulope.week_10_task.databinding.FragmentHomepageBinding
import com.droid.tolulope.week_10_task.model.data.PostAndPhoto
import com.droid.tolulope.week_10_task.utils.createListenerForComments
import com.droid.tolulope.week_10_task.utils.showToast
import com.droid.tolulope.week_10_task.viewmodel.HomepageViewModel

/**
 * This fragment displays all the post and photos saved in the database
 */
class HomepageFragment : Fragment(), RecyclerViewClickListener {

    private lateinit var homepageAdapter: HomepageAdapter
    private lateinit var viewModel: HomepageViewModel
    private var _binding: FragmentHomepageBinding? = null
    private val binding
    get() = _binding!!
    private val repository by lazy { App.repository }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomepageViewModel::class.java]

        /**
         * Initializes the adapter and creates a listener to navigate to the postDetails
         * fragment
         */
        homepageAdapter = HomepageAdapter(requireContext(), this)
        binding.postRecycler.adapter = homepageAdapter

        /**
         * Observes for changes in the list of post and updates the list in the adapter accordingly
         * which in turn handles the ui display
         */
        viewModel.postAndPhotoList.observe(viewLifecycleOwner, {
            if (it != null) {
                homepageAdapter.setupPosts(it as MutableList<PostAndPhoto>)
                binding.postRecycler.visibility = View.VISIBLE
            } else {
                showToast(requireContext(), "An error occured")
            }
            binding.progressBar.visibility = View.GONE
        })
    }

    override fun onRecyclerViewItemClicked(view: View, post: PostAndPhoto) {
        val fragId = 1
        createListenerForComments(view,post,fragId)
    }

    // Destroys binding cause fragments outlive their views and to prevent network leaks or crashes
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}