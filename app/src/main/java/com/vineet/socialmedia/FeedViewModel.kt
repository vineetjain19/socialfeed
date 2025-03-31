package com.vineet.socialmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {
    private var currentPage = 0
    private val pageSize = 5
    val posts = mutableListOf<Post>()
    private val _posts = MutableLiveData<List<Post>>()
    val networkPosts: LiveData<List<Post>> get() = _posts

    init {
        loadMorePosts()
    }
    fun loadMorePosts() {
        val newPosts = StaticDataSource.getPosts(currentPage * pageSize, pageSize)
        posts.addAll(newPosts)
        currentPage++
    }

    //for network call
    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                _posts.value = RetrofitClient.apiService.getPosts().take(5) // Load 5 posts initially
            } catch (e: Exception) {
                e.printStackTrace() // Log error
            }
        }
    }
}