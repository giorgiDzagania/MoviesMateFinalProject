package com.example.testproject.presentation.screens.screens.loginPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testproject.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LogInFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

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
        setCollectors()
    }


    private fun logInUser() {
        binding.btnHomePage.setOnClickListener {
            val email = binding.userEmail.text.toString()
            val password = binding.password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.loginUser(email, password)
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            // Observe successful registration
            viewModel.loginEvent.collect {
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            // Observe errors and show them to the user
            viewModel.showError.collect { errorMessage ->
                if (!errorMessage.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                    // Reset UI if needed (e.g., clear input fields or focus on email)
                    binding.userEmail.requestFocus()
                }
            }
        }
    }
}