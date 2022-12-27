package com.example.aslcodingtestproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aslcodingtestproject.constant.util.OnCustomItemClickListener
import com.example.aslcodingtestproject.databinding.ItemPhotoHolderThumbnailBinding
import com.example.aslcodingtestproject.view.viewdata.PhotoItem

//Cus adapter for list

class PhotoListAdapter(
    private val onCustomItemClickListener: OnCustomItemClickListener<PhotoItem>,
) : ListAdapter<PhotoItem, PhotoListAdapter.ItemViewHolder>(PhotoListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutBinding =
            ItemPhotoHolderThumbnailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        return ItemViewHolder(layoutBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        ViewCompat.setTransitionName(holder.itemView, getItem(position).id.toString())
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ItemPhotoHolderThumbnailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PhotoItem) {

            binding.photo = item
            binding.executePendingBindings()

            binding.cvPhoto.setOnClickListener {
                onCustomItemClickListener.onClick(it, item)
            }
        }
    }
}

class PhotoListDiffCallback : DiffUtil.ItemCallback<PhotoItem>(){
    override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }

}