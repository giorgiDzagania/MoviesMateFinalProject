package com.example.testproject.presentation.screens.screens.homePage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testproject.data.remote.dto.PopularMoviesDto
import com.example.testproject.databinding.ItemPopularMoviesBinding

class HomePageAdapter :
    ListAdapter<PopularMoviesDto.ResultDto, HomePageAdapter.HomePageViewHolder>(DiffUtilCallBack()) {

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
        fun bind(result: PopularMoviesDto.ResultDto) {
            val imageUrl = "https://image.tmdb.org/t/p/w500${result.backdrop_path}"
            Glide.with(binding.moviePoster)
                .load(imageUrl)
                .into(binding.moviePoster)
            binding.movieTitle.text = result.title
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<PopularMoviesDto.ResultDto>() {
        override fun areItemsTheSame(
            oldItem: PopularMoviesDto.ResultDto,
            newItem: PopularMoviesDto.ResultDto
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PopularMoviesDto.ResultDto,
            newItem: PopularMoviesDto.ResultDto
        ): Boolean {
            return oldItem == newItem
        }

    }
}