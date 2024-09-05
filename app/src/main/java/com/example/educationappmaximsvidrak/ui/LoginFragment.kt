package com.example.educationappmaximsvidrak.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.educationappmaximsvidrak.LoginViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.databinding.FragmentLoginBinding
import com.google.android.material.animation.AnimationUtils


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {

            val email = binding.tietEmail.text.toString()
            val pass = binding.tietPassword.text.toString()

            val animation = android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.shake)

            if (email != ""&& Patterns.EMAIL_ADDRESS.matcher(email).matches() && pass != "") {
                viewModel.login(email, pass)
            } else  {
            // Если email некорректен
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                Toast.makeText(requireContext(), "Enter correct email", Toast.LENGTH_SHORT).show()
                binding.tietEmail.startAnimation(animation)
            }

            if (email.isEmpty()) {
                binding.tietEmail.startAnimation(animation)
            }
            if (pass.isEmpty()) {
                binding.tietPassword.startAnimation(animation)
            }
        }


        }

        // Наблюдение за результатом входа
        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
        }



        binding.tvSignIn.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        // Wenn User eingeloggt wird vom Login-Screen weg-navigiert
        viewModel.currentUser.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }
        }


    }

}