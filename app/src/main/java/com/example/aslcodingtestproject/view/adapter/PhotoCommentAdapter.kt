package com.example.aslcodingtestproject.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aslcodingtestproject.R
import com.example.aslcodingtestproject.databinding.ItemPhotoHolderCommentsBinding
import com.example.aslcodingtestproject.view.viewdata.PhotoDetailItem

//Cus adapter for comment

class PhotoCommentAdapter(
    private val act: Context,
) : ListAdapter<PhotoDetailItem, PhotoCommentAdapter.ItemViewHolder>(PhotoDetailListDiffCallback()) {

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

        fun bind(item: PhotoDetailItem, position: Int) {
            binding.tvComment.text =  act.getString(R.string.common_comment_with_colon, (position+1).toString())
            binding.photoDetail = item
            binding.executePendingBindings()
        }

    }

}

class PhotoDetailListDiffCallback : DiffUtil.ItemCallback<PhotoDetailItem>(){
    override fun areItemsTheSame(oldItem: PhotoDetailItem, newItem: PhotoDetailItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoDetailItem, newItem: PhotoDetailItem): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }

}
