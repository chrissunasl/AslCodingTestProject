package com.example.aslcodingtestproject.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filterable
import androidx.core.net.toUri
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.aslcodingtestproject.constant.util.OnCustomItemClickListener
import com.example.aslcodingtestproject.databinding.ItemPhotoHolderCommentsBinding
import com.example.aslcodingtestproject.databinding.ItemPhotoHolderThumbnailBinding
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespX
import java.util.ArrayList
import java.util.concurrent.Executors
import kotlin.math.roundToInt

//Cus adapter for comment

class PhotoCommentAdapter(
    private val act: Context,
    private val onCustomItemClickListener: OnCustomItemClickListener<GetPhotoDetailRespItem>
) : RecyclerView.Adapter<PhotoCommentAdapter.ItemViewHolder>() {

    var dataList: MutableList<GetPhotoDetailRespItem> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: MutableList<GetPhotoDetailRespItem>) {
        this.dataList = list
        Log.d("chris", "addList, list: ${this.dataList.toString()}")
        notifyDataSetChanged()
    }

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
        val data = dataList[position]
        holder.bind(data, position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ItemViewHolder(private val binding: ItemPhotoHolderCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GetPhotoDetailRespItem, position: Int) {
            Log.d("chris", "bind item: ${item}")
//            Glide.with(act.applicationContext)
//                .asBitmap()
//                .load(item.thumbnailUrl)
//                .optionalFitCenter()
//                .into(binding.ivPhoto)



            //binding.tvTitle.text = item.title
            binding.tvComment.text = "Comment: " + item.id
            binding.tvCommentDetail.text = item.body
            binding.cvPhoto.setOnClickListener {
                onCustomItemClickListener.onClick(it, item)
            }
        }

    }

}