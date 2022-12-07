package com.example.aslcodingtestproject.constant.util

import android.view.View

// interface of common listener collection
interface OnViewClickListener {
    fun onClick()
}

interface OnCustomItemClickListener<T> {
    fun onClick(view: View?, item: T)
}

interface OnItemCheckedListener<T : Any> {
    fun onCheck(view: View?, item: T, isChecked: Boolean)
}

interface OnValueInputCallback<T : Any> {
    fun onValue(view: View?, valueMap: T, position: Int)
}

interface OnValueCallback<T : Any> {
    fun onValue(view: View?, item: T)
}

interface OnDeleteClickListener<T> {
    fun onClick(view: View?, item: T)
}

interface OnMoveClickListener<T> {
    fun onClick(view: View?, item: T)
}

interface OnCheckListener<T>{
    fun onItemCheckChanged(item: T,isChecked: Boolean)
}

interface OnCheckChangedListener<T>{
    fun onItemCheckChanged(item: T,position: Int,isChecked: Boolean)
}

interface OnCancelClickListener<T>{
    fun onClick(item: T)
}