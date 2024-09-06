package com.example.educationappmaximsvidrak.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.educationappmaximsvidrak.LoginViewModel
import com.example.educationappmaximsvidrak.databinding.FragmentPersonalInfoBinding
import com.example.educationappmaximsvidrak.model.Profile
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class PersonalInfoFragment : Fragment() {

    private lateinit var binding: FragmentPersonalInfoBinding
    private val loginViewModel: LoginViewModel by activityViewModels()

    private lateinit var profileClass: Profile


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.ibBack.setOnClickListener {
            findNavController().navigate(PersonalInfoFragmentDirections.actionPersonalInfoFragmentToSettingsFragment())
        }

        binding.btnUpdateContact.setOnClickListener {
            saveData()
        }

    }


    private fun saveData() {
        val firstName = binding.tietFirstName.text.toString()
        val secondName = binding.tietSecondName.text.toString()
        val phoneNumber = binding.tietPhoneNumber.text.toString()

        if (firstName.isEmpty()) binding.tietFirstName.error = "write a first name"
        if (secondName.isEmpty()) binding.tietSecondName.error = "write a second name"
        if (phoneNumber.isEmpty()) binding.tietFirstName.error = "write a phone number"


        val firebaseRef = loginViewModel.firebaseRef

        val contactId = firebaseRef.push().key!!
        val contacts = Profile(contactId, firstName, secondName, phoneNumber)

        firebaseRef.push().setValue(contacts).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "Data saved successfully")
            } else {
                Log.e("Firebase", "Failed to save data", task.exception)
            }
        }
    }


}