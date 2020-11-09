package com.soldemom.outstagram.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.outstagram_item_view.view.*

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val postOwner = itemView.post_owner
    val postContent = itemView.post_content
    val postImg = itemView.post_img

}