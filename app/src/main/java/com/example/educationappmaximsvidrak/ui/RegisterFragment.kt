package com.example.educationappmaximsvidrak.ui

import android.os.Bundle
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
import com.google.firebase.auth.FirebaseAuth

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
            val email = binding.registerEmail.text.toString()
            val pass = binding.registerPassword.text.toString()

            val animation = android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.shake)


            if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && pass.isNotEmpty()) {
                //TODO Patterns.EMAIL_ADDRESS.matcher(email).matches() - prüfen, ob die Adresse im E-Mail-Format eingegeben wurde
                viewModel.register(email, pass)
            }  else {
                // TODO Wenn die E-Mail falsch ist
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(requireContext(), "Enter correct email", Toast.LENGTH_SHORT).show()
                    binding.registerEmail.startAnimation(animation)
                }
                if (email.isEmpty()) {
                    binding.registerEmail.startAnimation(animation)
                }
                if (pass.isEmpty()) {
                    binding.registerPassword.startAnimation(animation)
                }
            }

        }

        viewModel.currentUser.observe(viewLifecycleOwner) {firebaseUser ->
            if (firebaseUser != null ) {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
                Toast.makeText(context, "You have successfully registered", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

    }

}