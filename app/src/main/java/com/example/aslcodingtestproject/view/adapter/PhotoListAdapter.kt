package com.example.aslcodingtestproject.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.aslcodingtestproject.constant.util.OnCustomItemClickListener
import com.example.aslcodingtestproject.databinding.ItemPhotoHolderThumbnailBinding
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem

//Cus adapter for list

class PhotoListAdapter(
    private val onCustomItemClickListener: OnCustomItemClickListener<GetPhotoRespItem>,
) : RecyclerView.Adapter<PhotoListAdapter.ItemViewHolder>() {

    private var dataList: ArrayList<GetPhotoRespItem> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: ArrayList<GetPhotoRespItem>) {
        this.dataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutBinding =
            ItemPhotoHolderThumbnailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        return ItemViewHolder(layoutBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = dataList[position]
        ViewCompat.setTransitionName(holder.itemView, dataList[position].id.toString())
        holder.bind(data, position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ItemViewHolder(private val binding: ItemPhotoHolderThumbnailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GetPhotoRespItem, position: Int) {

            binding.photo = item
            binding.executePendingBindings()

            binding.cvPhoto.setOnClickListener {
                onCustomItemClickListener.onClick(it, item)
            }
        }
    }
}