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
import com.google.firebase.auth.FirebaseAuth
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

        getPersonInfo()


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

        if (firstName.isEmpty()) binding.tietFirstName.error = "Write a first name"
        if (secondName.isEmpty()) binding.tietSecondName.error = "Write a username name"
        if (phoneNumber.isEmpty()) binding.tietFirstName.error = "Write a phone number"

        val firebaseRef = loginViewModel.firebaseRef

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = firebaseUser?.uid // Получаем uid пользователя

        if (userId != null) {
            val contacts = Profile(userId, firstName, secondName, phoneNumber)

            // Сохраняем данные под ключом userId
            firebaseRef.child(userId).setValue(contacts).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "Data saved successfully")
                } else {
                    Log.e("Firebase", "Failed to save data", task.exception)
                }
            }
        }

    }

    private fun getPersonInfo () {

        // Получаем текущего авторизованного пользователя
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = firebaseUser?.uid // Получаем уникальный идентификатор пользователя (uid)

        // Проверяем, что uid не равен null
        if (userId != null) {
            // Получаем ссылку на узел с данными пользователя
            val firebaseRef = loginViewModel.firebaseRef

            // Загрузка данных пользователя по его userId
            firebaseRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Получаем данные профиля пользователя и заполняем ими UI
                        val profile = snapshot.getValue(Profile::class.java)
                        if (profile != null) {
                            // Заполняем UI данными профиля
                            binding.tietFirstName.setText(profile.firstName)
                            binding.tietSecondName.setText(profile.username)  // Это "второе имя"
                            binding.tietPhoneNumber.setText(profile.phoneNumber)
                        } else {
                            Log.e("Firebase", "Profile data is null")
                        }
                    } else {
                        Log.e("Firebase", "Profile data not found")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error loading profile: ${error.message}")
                }
            })
        } else {
            Log.e("Firebase", "User ID is null")
        }


    }
}