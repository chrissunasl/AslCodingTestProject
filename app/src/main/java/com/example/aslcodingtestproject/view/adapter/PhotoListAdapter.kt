package com.example.aslcodingtestproject.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.aslcodingtestproject.R
import com.example.aslcodingtestproject.constant.util.OnCustomItemClickListener
import com.example.aslcodingtestproject.converter.ImageHandler.bindImageWithUrl
import com.example.aslcodingtestproject.databinding.ItemPhotoHolderThumbnailBinding
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespX
import com.squareup.picasso.Picasso
import java.util.ArrayList
import java.util.concurrent.Executors
import kotlin.math.roundToInt

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

//            item.thumbnailUrl.let {
//                val imgUri = item.thumbnailUrl.toUri().buildUpon().scheme("https").build()
//                binding.ivPhoto.load(imgUri) {
//                    placeholder(R.drawable.loading_animation)
//                    error(R.drawable.ic_broken_image)
//                }
//            }

//            Picasso.get()
//                .load(item.thumbnailUrl.toUri().buildUpon().scheme("https").build())
//                .placeholder(R.drawable.loading_animation)
//                .error(R.drawable.ic_broken_image)
//                .into(binding.ivPhoto);

            binding.cvPhoto.setOnClickListener {
                onCustomItemClickListener.onClick(it, item)
            }
        }


    }

}