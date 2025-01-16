package com.example.testproject.presentation.screens.screens.homePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testproject.data.toMovies
import com.example.testproject.databinding.FragmentHomeBinding
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val popularMoviesAdapter = PopularMoviesAdapter()
    private val upcomingMoviesAdapter = UpcomingMoviesAdapter()
    private val viewmodel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerPopularMovies()
        prepareCarouselRecyclerView()
        setCollectors()
        goToDetailsFragment()
        clickToStar()
    }

    private fun prepareCarouselRecyclerView() {
        binding.upcomingRv.setHasFixedSize(true)
        binding.upcomingRv.layoutManager = CarouselLayoutManager()
        CarouselSnapHelper().attachToRecyclerView(binding.upcomingRv)
        binding.upcomingRv.adapter = upcomingMoviesAdapter
    }

    private fun prepareRecyclerPopularMovies() {
        binding.popularMoviesRv.apply {
            adapter = popularMoviesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun clickToStar() {
        upcomingMoviesAdapter.onStarClick = { movie ->
            viewmodel.saveMovie(movie)
        }
    }

    private fun goToDetailsFragment() {
        popularMoviesAdapter.onItemClick = { movieId ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(movieId)
            )
        }
        upcomingMoviesAdapter.onItemClick = { movieId ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(movieId)
            )
        }
    }


    // ----------------------- Collectors ---------------
    private fun setCollectors() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.userEmail.collect {
                binding.userEmail.text = it
            }
        }

        // --- Popular Movie ---
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.popularMovies.collect {
                popularMoviesAdapter.submitList(it)
            }
        }

        // --- Upcoming Movies ---
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.upcomingMovies.collect {
                upcomingMoviesAdapter.submitList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.isLoadingState.collect { isLoading ->
                binding.progressBarPopularMovies.visibility =
                    if (isLoading) View.VISIBLE else View.GONE
                binding.progressBarUpcomingMovies.visibility =
                    if (isLoading) View.VISIBLE else View.GONE
            }
        }

    }
}