package com.example.testproject.presentation.screens.screens.searchPage

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testproject.data.remote.dto.MovieDto
import com.example.testproject.databinding.ItemAllMoviesBinding

class SearchAdapter : ListAdapter<MovieDto, SearchAdapter.MovieViewHolder>(MovieDiffCallback()) {

    var onItemClick: (movieId: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemAllMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieViewHolder(private val binding: ItemAllMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieDto) {
            // Check if posterPath is available and build the full image URL
            val posterUrl = movie.poster_path?.let {
                "https://image.tmdb.org/t/p/w500/$it"
            }
            Log.d("SearchAdapter", "Search Movie: ${movie.title}, Poster Path: ${movie.poster_path}")
            // Check if the URL is not null before loading the image with Glide
            posterUrl?.let {
                Glide.with(binding.ivMoviePoster.context)
                    .load(it)  // Use the full image URL here
                    .into(binding.ivMoviePoster)
            }
            binding.tvMovieTitle.text = movie.title

            binding.root.setOnClickListener {
                movie.id.let {onItemClick.invoke(it)}
            }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieDto>() {
        override fun areItemsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
            return oldItem == newItem
        }
    }
}
