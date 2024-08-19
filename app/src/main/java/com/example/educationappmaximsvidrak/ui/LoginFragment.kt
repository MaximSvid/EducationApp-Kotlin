package com.example.educationappmaximsvidrak.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
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
        textColor()

        binding.btnLogin.setOnClickListener {
            val email = binding.tietEmail.text.toString()
            val pass = binding.tietPassword.text.toString()

            when {
                email.isEmpty() -> {
                    Toast.makeText(context, "Please fill in the email field", Toast.LENGTH_SHORT).show()
                }
                pass.isEmpty() -> {
                    Toast.makeText(context, "Please fill in the password field", Toast.LENGTH_SHORT).show()
                } email.isEmpty() && pass.isEmpty() -> {
                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }

            }



            // Wenn User eingeloggt wird vom Login-Screen weg-navigiert
            viewModel.currentUser.observe(viewLifecycleOwner) {
                if (it != null) {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                }
            }

            binding.tvSignIn.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }

        }


    }

    private fun textColor() {
        val text = "Don't have an account? Sign in"
        val textSignIn = binding.tvSignIn
        val spannable = SpannableString(text)

        // Устанавливаем цвет для части текста "Sign in"
        val start = text.indexOf("Sign in")
        val end = start + "Sign in".length
        spannable.setSpan(
            ForegroundColorSpan(Color.BLUE),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textSignIn.text = spannable
    }


}