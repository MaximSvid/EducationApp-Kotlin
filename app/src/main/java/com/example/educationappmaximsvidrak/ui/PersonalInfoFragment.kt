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
import com.example.educationappmaximsvidrak.databinding.FragmentPersonalInfoBinding
import com.example.educationappmaximsvidrak.model.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class PersonalInfoFragment : Fragment() {

    private lateinit var binding: FragmentPersonalInfoBinding
    private val loginViewModel: LoginViewModel by activityViewModels()

    private lateinit var storageRef: StorageReference
    private var uri: Uri? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalInfoBinding.inflate(layoutInflater, container, false)

        storageRef = FirebaseStorage.getInstance().getReference("Images")
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

        val addImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.ivProfilePicture.setImageURI(it)
            if (it != null) {
                uri = it
            }
        }

        binding.btnUpdateImage.setOnClickListener {
            addImage.launch("image/*")
        }

    }




    private fun saveData() {
        val firstName = binding.etName.text.toString()
        val secondName = binding.etUsername.text.toString()
        val phoneNumber = binding.etPhone.text.toString()

        if (firstName.isEmpty()) binding.etName.error = "Write a first name"
        if (secondName.isEmpty()) binding.etUsername.error = "Write a username name"
        if (phoneNumber.isEmpty()) binding.etPhone.error = "Write a phone number"

        val firebaseRef = loginViewModel.firebaseRef

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = firebaseUser?.uid // Получаем uid пользователя

        if (userId != null) {
            var contacts: Profile

            uri?.let {
                storageRef.child(userId).putFile(it)
                    .addOnSuccessListener { task ->
                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { url ->
                                Toast.makeText(requireContext(), "Image done", Toast.LENGTH_SHORT).show()
                                val imageUrl = url.toString()

                                contacts = Profile(userId, firstName, secondName, phoneNumber, imageUrl)

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
                            binding.etName.setText(profile.firstName)
                            binding.etUsername.setText(profile.username)  // Это "второе имя"
                            binding.etPhone.setText(profile.phoneNumber)
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