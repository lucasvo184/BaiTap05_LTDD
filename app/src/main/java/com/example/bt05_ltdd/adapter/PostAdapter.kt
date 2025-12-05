package com.example.bt05_ltdd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bt05_ltdd.R
import com.example.bt05_ltdd.model.Post

/**
 * Adapter cho RecyclerView để hiển thị danh sách Post
 */
class PostAdapter(
    private var posts: List<Post> = emptyList(),
    private val onItemClick: ((Post) -> Unit)? = null
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    
    /**
     * ViewHolder chứa các views của mỗi item
     */
    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPostId: TextView = itemView.findViewById(R.id.tvPostId)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvBody: TextView = itemView.findViewById(R.id.tvBody)
        
        fun bind(post: Post) {
            tvPostId.text = "ID: ${post.id}"
            tvTitle.text = post.title.replaceFirstChar { it.uppercase() }
            tvBody.text = post.body
            
            // Set click listener
            itemView.setOnClickListener {
                onItemClick?.invoke(post)
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }
    
    override fun getItemCount(): Int = posts.size
    
    /**
     * Cập nhật danh sách posts và refresh RecyclerView
     */
    fun updatePosts(newPosts: List<Post>) {
        posts = newPosts
        notifyDataSetChanged()
    }
}

