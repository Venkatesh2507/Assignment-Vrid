package com.example.assignmentvrid.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.assignmentvrid.R
import com.example.assignmentvrid.databinding.ActivityMainBinding
import com.example.assignmentvrid.db.BlogsDatabase
import com.example.assignmentvrid.ui.fragments.viewmodals.BlogRepository
import com.example.assignmentvrid.ui.fragments.viewmodals.BlogViewModel
import com.example.assignmentvrid.ui.fragments.viewmodals.BlogViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    lateinit var viewModel: BlogViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.blogNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        val blogRepository = BlogRepository(BlogsDatabase(this))
        val viewModelProviderFactory = BlogViewModelProviderFactory(blogRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[BlogViewModel::class.java]






    }
}