package com.example.educationappmaximsvidrak.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
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
import com.example.educationappmaximsvidrak.databinding.FragmentRegisterBinding
import com.example.educationappmaximsvidrak.model.Profile

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

        binding.btnRegist.setOnClickListener {
            val name = binding.tietName.text.toString().ifEmpty { "User" } // wenn das Namensfeld leer ist, den Namen als Benutzer festlegen
            val email = binding.tietEmail.text.toString()
            val pass = binding.tietPassword.text.toString()

            val animation = android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.shake)

            val firebaseRef = viewModel.firebaseRef
            val contactId = firebaseRef.push().key!!

            if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && pass.isNotEmpty() && name.isNotEmpty()) {
                //TODO Patterns.EMAIL_ADDRESS.matcher(email).matches() - prüfen, ob die Adresse im E-Mail-Format eingegeben wurde
                viewModel.register(email, pass)
                viewModel.addName(contactId, name)
            }  else {
                // Если email некорректен
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                    Toast.makeText(requireContext(), "Enter correct email", Toast.LENGTH_SHORT).show()
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

        viewModel.currentUser.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
            }
        }

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

    }

}