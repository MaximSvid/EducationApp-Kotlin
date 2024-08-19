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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.educationappmaximsvidrak.LoginViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: LoginViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textColor()

        binding.btnRegist.setOnClickListener {
            val email = binding.tietEmail.text.toString()
            val pass = binding.tietPassword.text.toString()

            if (email != "" && pass != "") {
                viewModel.register(email, pass)
            }
        }

        viewModel.currentUser.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
            }
        }

    }

    private fun textColor() {
        val text = "Registered already? Log in"
        val textSignIn = binding.tvLogin
        val spannable = SpannableString(text)

        // Устанавливаем цвет для части текста "Sign in"
        val start = text.indexOf("Log in")
        val end = start + "Log in".length
        spannable.setSpan(
            ForegroundColorSpan(Color.BLUE),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textSignIn.text = spannable
    }

}