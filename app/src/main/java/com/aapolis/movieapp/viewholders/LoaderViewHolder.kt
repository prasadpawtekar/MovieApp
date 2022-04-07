package com.aapolis.movieapp.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.aapolis.movieapp.data.Loader
import com.aapolis.movieapp.databinding.ViewHolderLoaderBinding

class LoaderViewHolder(val binding: ViewHolderLoaderBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(loaderMsg: Loader) {
        binding.tvMessage.text = loaderMsg.msg
    }
}