package com.example.testproject.presentation.screens.screens.homePage

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testproject.data.remote.RetrofitInstance
import com.example.testproject.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// TODO:  ////// ar dagvavisydes homepagedan ukan momxmarebels ar unda sheedzlos gadasvla tu gadava aplicaiidan unda gavides!
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homePageAdapter = HomePageAdapter()
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
        setCollectors()
    }

    private fun prepareRecyclerPopularMovies() {
        binding.popularMoviesRv.apply {
            adapter = homePageAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.userEmail.collect {
                binding.userEmail.text = it
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.popularMovies.collect {
                homePageAdapter.submitList(it?.results)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.isLoadingState.collect { isLoading ->
                binding.progressBarPopularMovies.visibility =
                    if (isLoading) View.VISIBLE else View.GONE
            }
        }

    }
}