package com.example.aslcodingtestproject.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.aslcodingtestproject.constant.util.OnCustomItemClickListener
import com.example.aslcodingtestproject.view.viewmanager.ImageHandler.bindImageWithUrl
import com.example.aslcodingtestproject.databinding.ItemPhotoHolderThumbnailBinding
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
import java.util.ArrayList

//Cus adapter for list

class PhotoListAdapter(
    private val act: Context,
    private val onCustomItemClickListener: OnCustomItemClickListener<GetPhotoRespItem>,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<PhotoListAdapter.ItemViewHolder>() {

    var dataList: MutableList<GetPhotoRespItem> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: MutableList<GetPhotoRespItem>) {
        this.dataList = list
        Log.d("chris", "addList, list: ${this.dataList.toString()}")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutBinding =
            ItemPhotoHolderThumbnailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        layoutBinding.lifecycleOwner = lifecycleOwner
        return ItemViewHolder(layoutBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data, position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ItemViewHolder(private val binding: ItemPhotoHolderThumbnailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GetPhotoRespItem, position: Int) {
            Log.d("chris", "bind item: ${item}")

            binding.tvTitle.text = item.title

            binding.photo = item
            binding.executePendingBindings()
            bindImageWithUrl(binding.ivPhoto, item.thumbnailUrl)


            binding.cvPhoto.setOnClickListener {
                onCustomItemClickListener.onClick(it, item)
            }
        }


    }

}