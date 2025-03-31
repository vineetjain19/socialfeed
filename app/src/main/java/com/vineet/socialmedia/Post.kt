package com.vineet.socialmedia

data class Post(val id: Int, val imageUrl: String, var likes: Int, var isLiked: Boolean, val comments: List<String>)
