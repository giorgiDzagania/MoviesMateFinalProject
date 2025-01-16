package com.example.testproject.presentation.screens.screens.homePage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testproject.databinding.ItemPopularMoviesBinding
import com.example.testproject.domain.model.Movies

class PopularMoviesAdapter :
    ListAdapter<Movies, PopularMoviesAdapter.HomePageViewHolder>(DiffUtilCallBack()) {

    var onItemClick: (movieId: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageViewHolder {
        return HomePageViewHolder(
            ItemPopularMoviesBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HomePageViewHolder(private val binding: ItemPopularMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movies) {
            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.image}"
            Glide.with(binding.moviePoster)
                .load(imageUrl)
                .into(binding.moviePoster)
            binding.movieTitle.text = movie.title

            binding.root.setOnClickListener {
                onItemClick.invoke(movie.id)
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