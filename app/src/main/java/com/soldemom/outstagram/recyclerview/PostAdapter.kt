package com.soldemom.outstagram.recyclerview

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soldemom.outstagram.R
import com.soldemom.outstagram.retrofit.Post

class PostAdapter(
    var postList: ArrayList<Post>
) : RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.outstagram_item_view, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        holder.postOwner.text = post.owner
        holder.postContent.text = post.content
        Glide.with(holder.itemView)
            .load(post.image ?: R.drawable.ic_android_black_24dp)
            .into(holder.postImg)

    }

    override fun getItemCount(): Int {
        return postList.size
    }
}