package com.example.assignmentvrid.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentvrid.ui.fragments.viewmodals.BlogViewModel
import com.example.assignmentvrid.adapters.BlogsAdapter
import com.example.assignmentvrid.activity.MainActivity
import com.example.assignmentvrid.databinding.FragmentSavedBlogsBinding
import com.google.android.material.snackbar.Snackbar


class SavedBlogsFragment : Fragment() {

    private lateinit var viewModel: BlogViewModel
    private lateinit var blogsAdapter: BlogsAdapter
    private lateinit var binding: FragmentSavedBlogsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val blog = blogsAdapter.differ.currentList[position]
                viewModel.deleteArticle(blog)
                Snackbar.make(view,"Successfully deleted article", Snackbar.LENGTH_LONG).apply{
                    setAction("Undo"){
                        viewModel.saveArticle(blog)
                    }
                    show()
                }

            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerView)
        }
        viewModel.getSavedBlogs().observe(viewLifecycleOwner, Observer {
                blogs->
            blogsAdapter.differ.submitList(blogs)
        })
    }

    private fun setupRecyclerView() {
        blogsAdapter = BlogsAdapter()
        binding.recyclerView.apply {
            adapter = blogsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBlogsBinding.inflate(inflater, container, false)
        return binding.root
    }

}