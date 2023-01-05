package com.example.aslcodingtestproject.presentation.photocomment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aslcodingtestproject.R
import com.example.aslcodingtestproject.databinding.ItemPhotoHolderCommentsBinding
import com.example.aslcodingtestproject.presentation.photocomment.PhotoCommentItem

//Cus adapter for comment

class PhotoCommentAdapter(
    private val context: Context,
) : ListAdapter<PhotoCommentItem, PhotoCommentAdapter.ItemViewHolder>(PhotoCommentListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutBinding =
            ItemPhotoHolderCommentsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ItemViewHolder(layoutBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ItemViewHolder(private val binding: ItemPhotoHolderCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PhotoCommentItem, position: Int) {
            binding.tvComment.text =  context.getString(R.string.common_comment_with_colon, (position+1).toString())
            binding.photoComment = item
            binding.executePendingBindings()
        }
    }
}

// A tool to optimize the performance of RecyclerView
class PhotoCommentListDiffCallback : DiffUtil.ItemCallback<PhotoCommentItem>(){
    override fun areItemsTheSame(oldItem: PhotoCommentItem, newItem: PhotoCommentItem): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: PhotoCommentItem, newItem: PhotoCommentItem): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}
