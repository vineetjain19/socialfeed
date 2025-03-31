package com.vineet.socialmedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter(private var posts: MutableList<Post>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int = posts.size

    fun addMorePosts(newPosts: List<Post>) {
        val startPosition = posts.size
        posts.addAll(newPosts)
        notifyItemRangeInserted(startPosition, newPosts.size)
    }

    fun updatePosts(newPosts: List<Post>) {
        posts = newPosts as MutableList<Post>
        notifyDataSetChanged()
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val postImage: ImageView = itemView.findViewById(R.id.post_image)
        private val likeButton: Button = itemView.findViewById(R.id.like_button)
        private val likeCount: TextView = itemView.findViewById(R.id.like_count)
        private val commentsSection: TextView = itemView.findViewById(R.id.comments_section)

        fun bind(post: Post) {
            Glide.with(itemView.context)
                .load(post.imageUrl)
                .into(postImage)

            likeCount.text = "Likes: ${post.likes}"
            commentsSection.text = post.comments.joinToString("\n")
            likeButton.text = if (post.isLiked) "Unlike" else "Like"

            likeButton.setOnClickListener {
                post.isLiked = !post.isLiked
                post.likes += if (post.isLiked) 1 else -1
                likeCount.text = "Likes: ${post.likes}"
                likeButton.text = if (post.isLiked) "Unlike" else "Like"
            }
        }
    }
}