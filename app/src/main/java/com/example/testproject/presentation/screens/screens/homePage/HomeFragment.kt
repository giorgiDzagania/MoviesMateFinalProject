package com.example.testproject.presentation.screens.screens.homePage

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testproject.data.remote.RetrofitInstance
import com.example.testproject.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val service = RetrofitInstance.moviesService
    private val homePageAdapter = HomePageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayCurrentUserEmail()

        prepareRecyclerPopularMovies()
        displayPopularMovies()
    }

    private fun prepareRecyclerPopularMovies() {
        binding.popularMoviesRv.apply {
            adapter = homePageAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun displayPopularMovies() = viewLifecycleOwner.lifecycleScope.launch {
        service.getPopularMovies()
        homePageAdapter.submitList(service.getPopularMovies().body()?.results)
        d("lol","${service.getPopularMovies().body()?.results}")
    }

    private fun displayCurrentUserEmail() {
        binding.userEmail.text = firebaseAuth.currentUser?.email ?: ""
    }

}