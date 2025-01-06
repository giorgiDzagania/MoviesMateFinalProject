package com.example.testproject.presentation.screens.screens

import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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

        //FirebaseAuth.getInstance().signOut()
        d("MyLog", user.currentUser?.email.toString())

    }
}


/*
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize SplashScreen
        val splashScreen = installSplashScreen()

        // Check user status and navigate accordingly
        splashScreen.setKeepOnScreenCondition {
            // Return true for keeping splash screen, or false for transitioning
            firebaseAuth.currentUser != null
        }

        // After splash screen, check if the user is authenticated
        val user = firebaseAuth.currentUser
        if (user != null) {
            // User is logged in, navigate to HomeActivity
            navigateToHomePage()
        } else {
            // User is not logged in, navigate to LoginActivity
            navigateToRegisterPage()
        }

        // Set up binding and content view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun navigateToRegisterPage() {
        val intent = Intent(this, RegisterFragment::class.java)
        startActivity(intent)
        finish() // Prevent going back to MainActivity
    }

    private fun navigateToHomePage() {
        val intent = Intent(this, HomeFragment::class.java)
        startActivity(intent)
        finish() // Prevent going back to MainActivity
    }
}*/
