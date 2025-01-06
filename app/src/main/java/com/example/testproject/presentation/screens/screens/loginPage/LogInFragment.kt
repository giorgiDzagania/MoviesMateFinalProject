package com.example.testproject.presentation.screens.screens.loginPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testproject.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logInUser()
    }

    private fun logInUser() {
        binding.btnHomePage.setOnClickListener {
            val email = binding.userEmail.text.toString()
            val password = binding.password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}