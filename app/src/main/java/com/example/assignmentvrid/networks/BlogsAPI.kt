package com.example.assignmentvrid.networks

import com.example.assignmentvrid.modal.Blog
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BlogsAPI {
    @GET("wp-json/wp/v2/posts?")
    suspend fun getBlogs(@Query("per_page") perPage: Int=10,@Query("page") page: Int): Response<List<Blog>>


}