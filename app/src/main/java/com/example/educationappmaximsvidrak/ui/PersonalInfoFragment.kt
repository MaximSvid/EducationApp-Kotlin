package com.example.educationappmaximsvidrak.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.educationappmaximsvidrak.LoginViewModel
import com.example.educationappmaximsvidrak.R
import com.example.educationappmaximsvidrak.databinding.FragmentPersonalInfoBinding
import com.example.educationappmaximsvidrak.model.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlin.math.log

class PersonalInfoFragment : Fragment() {

    private lateinit var binding: FragmentPersonalInfoBinding
    private val loginViewModel: LoginViewModel by activityViewModels()

    private var uri: Uri? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        loginViewModel.getProfileInfo()


        binding.ibBack.setOnClickListener {
            findNavController().navigate(PersonalInfoFragmentDirections.actionPersonalInfoFragmentToSettingsFragment())
        }



        val addImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.ivProfilePicture.setImageURI(it)
            if (it != null) {
                uri = it
            }
        }

        binding.btnUpdateImage.setOnClickListener {
            addImage.launch("image/*")
        }

        binding.btnUpdateContact.setOnClickListener {
            val firstName = binding.etName.text.toString()
            val secondName = binding.etUsername.text.toString()
            val phoneNumber = binding.etPhone.text.toString()
            if (firstName.isNotEmpty() && secondName.isNotEmpty() && phoneNumber.isNotEmpty()) {
                loginViewModel.updateProfile(firstName, secondName, phoneNumber, uri)
            }

        }

    }

    private fun observeViewModel() {
        loginViewModel.profileLiveData.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                binding.etName.setText(it.firstName)
                binding.etUsername.setText(it.username)
                binding.etPhone.setText(it.phoneNumber)
                binding.ivProfilePicture.setImageURI(uri)
            }
        }

        loginViewModel.updateStatus.observe(viewLifecycleOwner) { isSuccessful ->
            if (isSuccessful) {
                Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show()
            }
        }
    }






}