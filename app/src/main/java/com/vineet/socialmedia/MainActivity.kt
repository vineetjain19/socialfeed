package com.vineet.socialmedia

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.lifecycle.Observer
import com.vineet.socialmedia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: PostAdapter
    private lateinit var viewModel: FeedViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)        //initialize

        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        adapter = PostAdapter(viewModel.posts)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        //for network call
        viewModel.networkPosts.observe(this) { posts ->
            adapter.updatePosts(posts)
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
                    loadMoreItems()
                }
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshFeed()
        }
    }

    private fun loadMoreItems() {
        val newPosts = StaticDataSource.getPosts(viewModel.posts.size, 5)
        viewModel.posts.addAll(newPosts)
        adapter.addMorePosts(newPosts)
    }

    private fun refreshFeed() {
        GlobalScope.launch(Dispatchers.Main) {
            kotlinx.coroutines.delay(2000) // Simulating network delay
            viewModel.posts.clear()
            viewModel.posts.addAll(StaticDataSource.getPosts(0, 10)) // Reload initial data
            adapter.notifyDataSetChanged()
            binding.swipeRefreshLayout.isRefreshing = false
            Toast.makeText(this@MainActivity, "Feed refreshed!", Toast.LENGTH_SHORT).show()
        }
    }
}