package com.dada.dadacomponentlibrary.adapter.base
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseViewHolder<VB : ViewBinding>(var binding: VB) : RecyclerView.ViewHolder(
    binding.root
)