package com.hell.socialapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.hell.socialapp.daos.PostDao

class AddPostActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        postDao = PostDao()
        editText = findViewById(R.id.input)
        button = findViewById(R.id.button)
        button.setOnClickListener{
            val post = editText.text.toString()
            if(post.isNotEmpty()){
                postDao.addPost(post)
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}