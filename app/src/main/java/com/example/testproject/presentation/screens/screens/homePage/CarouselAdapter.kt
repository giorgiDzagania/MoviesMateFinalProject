package com.example.testproject.presentation.screens.screens.homePage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testproject.data.remote.dto.PopularMoviesDto
import com.example.testproject.databinding.ItemTrendingMoviesBinding

class CarouselAdapter :
    ListAdapter<PopularMoviesDto.ResultDto, CarouselAdapter.CarouselViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        return CarouselViewHolder(
            ItemTrendingMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CarouselViewHolder(private val binding: ItemTrendingMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: PopularMoviesDto.ResultDto) {
            val imageUrl = "https://image.tmdb.org/t/p/w500${result.backdrop_path}"
            Glide.with(binding.moviePoster)
                .load(imageUrl)
                .into(binding.moviePoster)
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