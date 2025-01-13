package com.example.testproject.presentation.screens.screens.homePage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testproject.data.remote.dto.UpcomingMoviesDto
import com.example.testproject.databinding.ItemUpcomingMoviesBinding

class UpcomingMoviesAdapter :
    ListAdapter<UpcomingMoviesDto.Result, UpcomingMoviesAdapter.UpcomingViewHolder>(DiffUtilCallBack()) {

    var onItemClick: (movieId: Int) -> Unit = {}

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
        fun bind(result: UpcomingMoviesDto.Result) {
            val imageUrl = "https://image.tmdb.org/t/p/w500${result.backdrop_path}"
            Glide.with(binding.moviePoster)
                .load(imageUrl)
                .into(binding.moviePoster)

            binding.root.setOnClickListener {
                result.id.let {onItemClick.invoke(it)}
            }
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<UpcomingMoviesDto.Result>() {
        override fun areItemsTheSame(
            oldItem: UpcomingMoviesDto.Result,
            newItem: UpcomingMoviesDto.Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UpcomingMoviesDto.Result,
            newItem: UpcomingMoviesDto.Result
        ): Boolean {
            return oldItem == newItem
        }


    }

}