package com.example.testproject.presentation.screens.screens.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testproject.databinding.ItemFavoritesBinding
import com.example.testproject.domain.model.Movies

class FavoritesAdapter :
    ListAdapter<Movies, FavoritesAdapter.FavoritesViewHolder>(DiffUtilCallBack()) {

    var onItemClick: (movies: Movies) -> Unit = {}

    var onStarClick: (movies: Movies) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            ItemFavoritesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Movies>() {
        override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem == newItem
        }
    }

    inner class FavoritesViewHolder(private val binding: ItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: Movies) {
            binding.movieTitle.text = movies.title
            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/w500${movies.image}")
                .into(binding.moviePoster)

            binding.btnFavorite.setOnClickListener {
                onStarClick.invoke(movies)
            }

            binding.root.setOnClickListener {
                onItemClick.invoke(movies)
            }
        }
    }

}