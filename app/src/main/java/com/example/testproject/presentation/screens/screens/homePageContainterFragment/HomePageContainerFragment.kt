package com.example.testproject.presentation.screens.screens.homePageContainterFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.testproject.R
import com.example.testproject.databinding.FragmentHomePageBinding

class HomePageContainerFragment : Fragment() {
    private lateinit var binding: FragmentHomePageBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("HomePageFragment", "onViewCreated called") // Log entry point
        setUp()
    }

    private fun setUp() {
        Log.d("HomePageFragment", "setUp called") // Log setup initialization
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragmentContainerView2) as? NavHostFragment

        if (navHostFragment != null) {
            navController = navHostFragment.navController
            Log.d("HomePageFragment", "NavController initialized: ${navController.graph}")
        } else {
            Log.e("HomePageFragment", "NavHostFragment is null")
            return
        }

        // Set up BottomNavigationView with NavController
        try {
            binding.bottomNavigation.setupWithNavController(navController)
            Log.d("HomePageFragment", "BottomNavigationView set up successfully")
        } catch (e: Exception) {
            Log.e("HomePageFragment", "Error setting up BottomNavigationView", e)
        }
    }
}
