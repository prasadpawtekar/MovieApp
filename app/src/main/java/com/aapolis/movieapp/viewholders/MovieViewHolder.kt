package com.aapolis.movieapp.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.aapolis.movieapp.Constants
import com.aapolis.movieapp.R
import com.aapolis.movieapp.data.response.Movie
import com.aapolis.movieapp.databinding.ViewHolderMovieBinding
import com.bumptech.glide.Glide

class MovieViewHolder(val binding: ViewHolderMovieBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.tvTitle.text = movie.title
        binding.tvRd.text = movie.release_date
        binding.tvOverview.text = movie.overview

        val posterUrl = "${Constants.IMAGE_BASE_URL}/w300/${movie.poster_path}"

        Glide.with(binding.root)
            .load(posterUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.ivPoster)
    }
}