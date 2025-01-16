package com.example.testproject.presentation.screens.screens.homePage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testproject.databinding.ItemUpcomingMoviesBinding
import com.example.testproject.domain.model.Movies

class UpcomingMoviesAdapter :
    ListAdapter<Movies, UpcomingMoviesAdapter.UpcomingViewHolder>(DiffUtilCallBack()) {

    var onItemClick: (movieId: Int) -> Unit = {}

    var onStarClick: (movie: Movies) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        return UpcomingViewHolder(
            ItemUpcomingMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UpcomingViewHolder(private val binding: ItemUpcomingMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movies) {
            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.image}"
            Glide.with(binding.moviePoster)
                .load(imageUrl)
                .into(binding.moviePoster)

            binding.root.setOnClickListener {
                onItemClick.invoke(movie.id)
            }

            binding.favoritesStar.setOnClickListener {
                onStarClick.invoke(movie)
            }
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Movies>() {
        override fun areItemsTheSame(
            oldItem: Movies,
            newItem: Movies
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Movies,
            newItem: Movies
        ): Boolean {
            return oldItem == newItem
        }

    }

}