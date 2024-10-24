package com.example.assignmentvrid.ui.fragments.viewmodals

import com.example.assignmentvrid.db.BlogsDatabase
import com.example.assignmentvrid.modal.Blog
import com.example.assignmentvrid.networks.RetrofitInstance

class BlogRepository(
    val db : BlogsDatabase
) {

    suspend fun getBlogs(pageNumber: Int) = RetrofitInstance.api.getBlogs(10,pageNumber)
    suspend fun insert(blog: Blog) = db.getBlogsDao().insert(blog)
    fun getSavedBlogs() = db.getBlogsDao().getAllArticles()
    suspend fun deleteArticle(blog: Blog) = db.getBlogsDao().deleteArticle(blog)


}