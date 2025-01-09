package com.example.testproject.presentation.screens.screens.registerPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testproject.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        registerNewUser()
        redirectToLogInPage()
        setCollectors()
    }

    // ---------------------- Redirect To LogIn Page -----------------
    private fun redirectToLogInPage() {
        binding.btnLoginPage.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLogInFragment())
        }
    }

    // ----------------------- Register User --------------------
    private fun registerNewUser() {
        binding.btnRegister.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val repeatPassword = binding.repeatPassword.text.toString()
            if (email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (password != repeatPassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            viewModel.registerNewUser(email, password)
        }
    }

    private fun setCollectors() {
        // SharedFlow
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.registerEvent.collect {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomePageFragment())
            }
        }

        // SharedFlow
        viewLifecycleOwner.lifecycleScope.launch {
            Log.d("check", Thread.currentThread().name)
            viewModel.showError.collect { errorMessage ->
                if (!errorMessage.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoadingState.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

    }
}
