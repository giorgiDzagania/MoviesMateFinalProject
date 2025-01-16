package com.example.testproject.presentation.screens.screens.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testproject.databinding.FragmentFavoritesBinding
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val favoritesAdapter = FavoritesAdapter()
    private val viewmodel by viewModels<FavoritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerview()
        setCollectors()
        deleteMovieFromFavorites()
        updateFavorites()
        goToDetailsFragment()
    }

    private fun goToDetailsFragment() {
        favoritesAdapter.onItemClick = {movie->
            findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(movie.id))
        }
    }

    private fun updateFavorites() {
        viewmodel.showAllSavedMovies()
    }

    private fun initRecyclerview() {
        binding.recyclerViewFavorites.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    private fun deleteMovieFromFavorites() {
        favoritesAdapter.onStarClick = { movie ->
            viewmodel.deleteSavedMovie(movie)
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.allSavedMovies.collect {
                favoritesAdapter.submitList(it)
            }
        }
    }

}