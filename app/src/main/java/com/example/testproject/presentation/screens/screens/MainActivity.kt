package com.example.testproject.presentation.screens.screens

import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.example.testproject.R
import com.example.testproject.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val user = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        FirebaseAuth.getInstance().signOut()
        d("MyLog" , "MainActivity ${FirebaseAuth.getInstance().signOut()}")
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        if (user.currentUser != null) {
            // User is already logged in, navigate to HomeFragment
            navGraph.setStartDestination(R.id.homeFragment)
        } else {
            // User is not logged in, navigate to RegisterFragment
            navGraph.setStartDestination(R.id.registerFragment)
        }
        navController.graph = navGraph
    }
}