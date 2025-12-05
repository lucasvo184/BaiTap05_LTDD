package com.example.bt05_ltdd

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bt05_ltdd.adapter.PostAdapter
import com.example.bt05_ltdd.model.Post
import com.example.bt05_ltdd.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    
    companion object {
        private const val TAG = "MainActivity"
    }
    
    // Khai báo views
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView
    
    // Adapter cho RecyclerView
    private lateinit var postAdapter: PostAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        // Setup window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Khởi tạo views
        initViews()
        
        // Setup RecyclerView
        setupRecyclerView()
        
        // Gọi API để lấy dữ liệu
        fetchPosts()
    }
    
    /**
     * Khởi tạo các views
     */
    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        tvError = findViewById(R.id.tvError)
    }
    
    /**
     * Cấu hình RecyclerView
     */
    private fun setupRecyclerView() {
        postAdapter = PostAdapter { post ->
            // Xử lý khi click vào item
            Toast.makeText(
                this,
                "Clicked: ${post.title}",
                Toast.LENGTH_SHORT
            ).show()
        }
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = postAdapter
        }
    }
    
    /**
     * Gọi API lấy danh sách posts sử dụng Retrofit
     */
    private fun fetchPosts() {
        // Hiển thị loading
        showLoading()
        
        // Gọi API
        RetrofitClient.apiService.getAllPosts().enqueue(object : Callback<List<Post>> {
            
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                // Ẩn loading
                hideLoading()
                
                if (response.isSuccessful) {
                    val posts = response.body()
                    if (posts != null && posts.isNotEmpty()) {
                        // Cập nhật adapter với dữ liệu mới
                        postAdapter.updatePosts(posts)
                        showContent()
                        Log.d(TAG, "Loaded ${posts.size} posts successfully")
                    } else {
                        showError("Không có dữ liệu")
                    }
                } else {
                    showError("Lỗi: ${response.code()}")
                    Log.e(TAG, "Error: ${response.code()} - ${response.message()}")
                }
            }
            
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                // Ẩn loading
                hideLoading()
                
                showError("Lỗi kết nối: ${t.message}")
                Log.e(TAG, "Network error: ${t.message}", t)
            }
        })
    }
    
    /**
     * Hiển thị loading indicator
     */
    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        tvError.visibility = View.GONE
    }
    
    /**
     * Ẩn loading indicator
     */
    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }
    
    /**
     * Hiển thị nội dung (RecyclerView)
     */
    private fun showContent() {
        recyclerView.visibility = View.VISIBLE
        tvError.visibility = View.GONE
    }
    
    /**
     * Hiển thị thông báo lỗi
     */
    private fun showError(message: String) {
        tvError.text = message
        tvError.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }
}
