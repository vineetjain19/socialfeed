package com.vineet.socialmedia

object StaticDataSource {
    private val allPosts = List(50) { index ->
        Post(index + 1, "https://upload.wikimedia.org/wikipedia/commons/f/f9/Phoenicopterus_ruber_in_S%C3%A3o_Paulo_Zoo.jpg", (5..50).random(), false, listOf("Comment ${index + 1}"))
    }

    fun getPosts(start: Int, count: Int): List<Post> {
        return allPosts.drop(start).take(count)
    }
}