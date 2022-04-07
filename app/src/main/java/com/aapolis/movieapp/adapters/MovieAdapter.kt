package com.aapolis.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aapolis.movieapp.data.Loader
import com.aapolis.movieapp.data.response.Movie
import com.aapolis.movieapp.databinding.ViewHolderLoaderBinding
import com.aapolis.movieapp.databinding.ViewHolderMovieBinding
import com.aapolis.movieapp.viewholders.LoaderViewHolder
import com.aapolis.movieapp.viewholders.MovieViewHolder
import java.lang.RuntimeException

class MovieAdapter(val items: ArrayList<Any>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        when(viewType) {
            VIEW_TYPE_MOVIE -> {
                val binding = ViewHolderMovieBinding.inflate(layoutInflater, parent, false)
                return MovieViewHolder(binding)
            }
            VIEW_TYPE_LOADER -> {
                val binding = ViewHolderLoaderBinding.inflate(layoutInflater, parent, false)
                return LoaderViewHolder(binding)
            }
        }

        throw RuntimeException("Unknown view type: $viewType")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is MovieViewHolder -> holder.bind(items[position] as Movie)
            is LoaderViewHolder -> holder.bind(items[position] as Loader)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        when(items[position]) {
            is Loader -> return VIEW_TYPE_LOADER
            is Movie -> return VIEW_TYPE_MOVIE
        }
        return super.getItemViewType(position)
    }
    companion object {
        const val VIEW_TYPE_MOVIE = 1
        const val VIEW_TYPE_LOADER = 2
    }

    fun addItems(moreItems: List<Any>) {
        items.addAll(moreItems)
        notifyDataSetChanged()
    }

    fun addLoader(loader: Loader) {
        items.add(loader)
        notifyItemChanged(items.size-1)
    }

    fun removeLoader() {
        if(items.get(items.size-1) is Loader) {
            items.removeLast()
            notifyDataSetChanged()
        }
    }
}