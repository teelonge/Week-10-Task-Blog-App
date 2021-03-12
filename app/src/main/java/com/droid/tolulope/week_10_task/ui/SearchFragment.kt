package com.droid.tolulope.week_10_task.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.droid.tolulope.week_10_task.App
import com.droid.tolulope.week_10_task.R
import com.droid.tolulope.week_10_task.ViewModelFactory
import com.droid.tolulope.week_10_task.adapter.HomepageAdapter
import com.droid.tolulope.week_10_task.databinding.FragmentSearchBinding
import com.droid.tolulope.week_10_task.model.data.PostAndPhoto
import com.droid.tolulope.week_10_task.utils.createListenerForComments
import com.droid.tolulope.week_10_task.utils.queryAllPosts
import com.droid.tolulope.week_10_task.utils.showToast
import com.droid.tolulope.week_10_task.viewmodel.HomepageViewModel

/**
 * This fragment takes in a query of characters and returns post in which the post title matches the
 * searched character sequence. Uses the homeAdapter and the list in the adapter is initially set up
 * with the post and photo from the database
 */
class SearchFragment : Fragment(), RecyclerViewClickListener {
    private lateinit var viewModel: HomepageViewModel
    private lateinit var searchView: SearchView
    private lateinit var homeAdapter: HomepageAdapter
    private val repository by lazy { App.repository }
    private var _binding: FragmentSearchBinding? = null
    private val binding
    get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Initializes the adapter and creates a listener to navigate to the postDetails
         * fragment
         */
        homeAdapter = HomepageAdapter(requireContext(), this)
        binding.searchRecycler.adapter = homeAdapter

        // Makes use of the homepageViewModel
        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomepageViewModel::class.java]

        /**
         * Observes for changes in the list of post and updates the list in the adapter accordingly
         * which in turn handles the ui display
         */
        viewModel.postAndPhotoList.observe(viewLifecycleOwner, {
            if (it != null) {
                homeAdapter.setupPosts(it as MutableList<PostAndPhoto>)
                binding.searchRecycler.visibility = View.VISIBLE
            } else {
                showToast(requireContext(), "An error occured")
            }
            binding.progressBar2.visibility = View.GONE
        })
    }

    /**
     * Creates the option menu and queries the list in the adapter to filter searched character
     * sequence
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.app_bar_search)
        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        queryAllPosts(homeAdapter, searchView)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRecyclerViewItemClicked(view: View, post: PostAndPhoto) {
        val fragId = 2
        createListenerForComments(view, post, fragId)
    }


}