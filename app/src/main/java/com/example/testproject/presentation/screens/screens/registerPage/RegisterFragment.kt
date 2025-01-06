package com.example.testproject.presentation.screens.screens.registerPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testproject.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val firebaseAuth = FirebaseAuth.getInstance()

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
        registerUser()
        redirectToLogInPage()
    }

    private fun redirectToLogInPage() {
        binding.btnLoginPage.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLogInFragment())
        }
    }

    private fun registerUser() {
        binding.btnRegister.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val repeatPassword = binding.repeatPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()
                && password == repeatPassword){
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener {
                        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                    }
            }

        }
    }
}