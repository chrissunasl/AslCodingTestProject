package com.example.aslcodingtestproject.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aslcodingtestproject.R
import com.example.aslcodingtestproject.databinding.ItemPhotoHolderCommentsBinding
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem

//Cus adapter for comment

class PhotoCommentAdapter(
    private val act: Context,
) : RecyclerView.Adapter<PhotoCommentAdapter.ItemViewHolder>() {

    var dataList: ArrayList<GetPhotoDetailRespItem> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: ArrayList<GetPhotoDetailRespItem>) {
        this.dataList = list
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
            binding.tvComment.text =  act.getString(R.string.common_comment_with_colon, item.id.toString())
            binding.tvCommentDetail.text = item.body
        }

    }

}