package com.hell.socialapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hell.socialapp.models.Post

class PostAdaptor(options: FirestoreRecyclerOptions<Post>,private val listener : IPostAdaptor) : FirestoreRecyclerAdapter<Post,PostAdaptor.PostViewHolder>(
    options
) {
    class PostViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val userImage : ImageView = itemView.findViewById(R.id.userImage)
        val userName : TextView = itemView.findViewById(R.id.userName)
        val createdAt : TextView = itemView.findViewById(R.id.createdAt)
        val postText : TextView = itemView.findViewById(R.id.postText)
        val likeButton : ImageView = itemView.findViewById(R.id.likeButton)
        val likeCount : TextView = itemView.findViewById(R.id.likeCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder = PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post,parent,false))
        viewHolder.likeButton.setOnClickListener{
            listener.onItemClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        Glide.with(holder.itemView.context).load(model.createdBy.imageUrl).circleCrop().into(holder.userImage)
        holder.userName.text = model.createdBy.displayName
        holder.createdAt.text = Utils.getTimeAgo(model.createdAt)
        holder.postText.text = model.text
        holder.likeCount.text = model.likedBy.size.toString()
        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid
        val isLiked = model.likedBy.contains(currentUserId)
        if(isLiked) {
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context,R.drawable.ic_like))
        }else{
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context,R.drawable.ic_unlike))
        }
    }
}

interface IPostAdaptor{
    fun onItemClicked(postId : String)
}