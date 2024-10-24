package com.example.assignmentvrid.ui.fragments.viewmodals

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentvrid.modal.Blog
import com.example.assignmentvrid.networks.ApiResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class BlogViewModel(
    val blogRepository: BlogRepository
) : ViewModel() {

    val blogs: MutableLiveData<ApiResponse<List<Blog>>?> = MutableLiveData()
    var pageNumber = 1

    var blogResponse: ArrayList<Blog>? = null


    init {
        getBlogs()
    }
    public fun getBlogs()=viewModelScope.launch {
        blogs.postValue(ApiResponse.ApiResponseLoading())
        val response = blogRepository.getBlogs(pageNumber)
        blogs.postValue(handleBlogsResponse(response))

    }

    private fun handleBlogsResponse(response: Response<List<Blog>>): ApiResponse<List<Blog>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                pageNumber++
                if (blogResponse == null) {
                    blogResponse = resultResponse as ArrayList<Blog>
                    Log.d("ViewModal", "handleBlogsResponse: "+blogResponse)
                } else {
                    val oldArticles = blogResponse
                    val newArticles = resultResponse
                    oldArticles?.addAll(newArticles)

                }
                return ApiResponse.ApiResponseSuccess(resultResponse)
            }

        }
        return ApiResponse.ApiResponseError(response.errorBody().toString())
    }
    fun saveArticle(blog: Blog) = viewModelScope.launch {
        blogRepository.insert(blog)
    }
    fun getSavedBlogs() = blogRepository.getSavedBlogs()
    fun deleteArticle(blog: Blog) = viewModelScope.launch {
        blogRepository.deleteArticle(blog)
    }

}