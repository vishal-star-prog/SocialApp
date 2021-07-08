package com.hell.socialapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query
import com.hell.socialapp.daos.PostDao
import com.hell.socialapp.models.Post

class MainActivity : AppCompatActivity(), IPostAdaptor {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fab : FloatingActionButton
    private lateinit var adaptor: PostAdaptor
    private lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        fab = findViewById(R.id.fab)
        fab.setOnClickListener{
            val intent = Intent(this,AddPostActivity::class.java)
            startActivity(intent)
        }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        postDao = PostDao()
        val postCollection = postDao.postCollection
        val query = postCollection.orderBy("createdAt",Query.Direction.DESCENDING)
        val recyclerViewOption = FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()
        adaptor = PostAdaptor(recyclerViewOption,this)
        recyclerView.adapter = adaptor
    }

    override fun onStart() {
        super.onStart()
        adaptor.startListening()
    }

    override fun onStop() {
        super.onStop()
        adaptor.stopListening()
    }

    override fun onItemClicked(postId: String) {
        postDao.onLikeClicked(postId)
    }
}