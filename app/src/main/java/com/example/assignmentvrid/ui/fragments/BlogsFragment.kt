package com.example.assignmentvrid.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentvrid.adapters.BlogsAdapter
import com.example.assignmentvrid.activity.MainActivity
import com.example.assignmentvrid.R
import com.example.assignmentvrid.databinding.FragmentBlogsBinding
import com.example.assignmentvrid.networks.ApiResponse
import com.example.assignmentvrid.ui.fragments.viewmodals.BlogViewModel


class BlogsFragment : Fragment() {

    private lateinit var viewModel: BlogViewModel
    private lateinit var blogsAdapter: BlogsAdapter
    private lateinit var binding: FragmentBlogsBinding
    private val QUERY_PAGE_SIZE = 10
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        blogsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_blogsFragment_to_articleFragment,bundle
            )

        }

        viewModel.blogs.observe(viewLifecycleOwner) { response ->

            val TAG = "BlogsFragment"
            when(response){
                is ApiResponse.ApiResponseSuccess -> {
                    hideProgressBar()
                    response.data?.let { blogsResponse ->
                        blogsAdapter.differ.submitList(blogsResponse)
                        val totalPages = blogsResponse.size / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.pageNumber == totalPages
                        Log.d(TAG, "onViewCreated: Success response")
                        if (isLastPage){
                            binding.recyclerView.setPadding(0,0,0,0)
                        }
                    }
                }
                is ApiResponse.ApiResponseError -> {

                    hideProgressBar()
                    response.message?.let { message ->
                        Log.d(TAG, "onViewCreated: $message")
                    }
                }
                is ApiResponse.ApiResponseLoading -> {
                    showProgressBar()

                    Log.d(TAG, "onViewCreated: Loading response")
                }

                null -> Log.d(TAG, "onViewCreated: Null response")
            }

        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.GONE

    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotBeginning = firstVisibleItemPosition>=0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE

            val shouldPaginate = isNotLoadingAndNotLastPage
                    && isAtLastItem && isNotBeginning
                    && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate){
                viewModel.getBlogs()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentBlogsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupRecyclerView() {
        blogsAdapter = BlogsAdapter()
        binding.recyclerView.apply {
            adapter = blogsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        binding.recyclerView.addOnScrollListener(this@BlogsFragment.scrollListener)
    }


}